package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckBodyDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckGetResponseDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckPostResponseDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.ProgressDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

public class AcrolinxRestConnectorImpl implements AcrolinxRestConnector {

  private static final Logger LOG = LoggerFactory.getLogger(AcrolinxRestConnectorImpl.class);

  private static final String CHECK_RESOURCE_PATH = "checking/checks/";
  private static final String SCHEME = "https";
  private static final String ACROLINX_CLIENT_HEADER = "X-Acrolinx-Client";
  private static final String ACROLINX_CLIENT_LOCALE_HEADER = "X-Acrolinx-Client-Locale";
  private static final String ACROLINX_CLIENT_AUTH_HEADER = "X-Acrolinx-Auth";
  private static final int MAX_RETRY_COUNT = 6;
  private static final int IN_PROGRESS_HTTP_STATUS_CODE = 202;
  private final RestTemplate restTemplate;

  public AcrolinxRestConnectorImpl(@NonNull RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  @Nullable
  public CheckGetResponseDocument checkText(@NonNull CheckBodyDocument data,
                                            @NonNull AcrolinxSettings acrolinxSettings) throws AcrolinxException {
    CheckPostResponseDocument postResponse = performPost(data, acrolinxSettings);
    if (postResponse == null || postResponse.getCheckDataDocument() == null || postResponse.getCheckDataDocument().getCheckId() == null) {
      return null;
    }

    return receiveDocumentAnalyze(postResponse.getCheckDataDocument().getCheckId(), acrolinxSettings);
  }

  @NonNull
  private ResponseEntity<CheckGetResponseDocument> receiveDocumentAnalyState(@NonNull String checkId, @NonNull AcrolinxSettings settings) throws AcrolinxException {
    String checkResorcePath = CHECK_RESOURCE_PATH + checkId;
    String url = buildUrl(checkResorcePath, getHost(settings));
    HttpEntity<String> requestEntity = buildGetEntity(settings);

    try {
      return restTemplate.exchange(url, HttpMethod.GET, requestEntity, CheckGetResponseDocument.class);
    } catch (HttpClientErrorException e) {
      throw new AcrolinxException("Failed to execute Acrolinx REST call: " +e.getMessage() +  "(response body: '" + e.getResponseBodyAsString() + ")", e);
    }
  }

  @Nullable
  private CheckGetResponseDocument receiveDocumentAnalyze(@NonNull String checkId, @NonNull AcrolinxSettings settings) throws AcrolinxException {
    CheckGetResponseDocument lastProgressStateHolder = null;
    for (int i = 1; i <= MAX_RETRY_COUNT; i++) {
      ResponseEntity<CheckGetResponseDocument> responseEntity = receiveDocumentAnalyState(checkId, settings);
      if (responseEntity.getStatusCodeValue() != IN_PROGRESS_HTTP_STATUS_CODE) {
        LOG.debug("Received analyze after " + i + " retries");
        return responseEntity.getBody();
      }
      LOG.debug("Retry: " + i + " with result: " + responseEntity.getBody());
      lastProgressStateHolder = responseEntity.getBody();
      waitSuggestedTime(responseEntity);
    }
    throw new AcrolinxException("Progress didn't finish after 6 retries, last received progress state: " + lastProgressStateHolder);
  }

  private void waitSuggestedTime(@NonNull ResponseEntity<CheckGetResponseDocument> responseEntity) throws AcrolinxException {
    CheckGetResponseDocument body = responseEntity.getBody();
    if (body == null) {
      return;
    }
    ProgressDocument progressDocument = body.getProgressDocument();
    if (progressDocument == null) {
      return;
    }
    int retryAfterSeconds = progressDocument.getRetryAfterSeconds();
    try {
      Thread.sleep(retryAfterSeconds * 1000);
    } catch (InterruptedException e) {
      throw new AcrolinxException("Exception occured while waiting to make next rest call", e);
    }
  }

  @Nullable
  private CheckPostResponseDocument performPost(@NonNull CheckBodyDocument bodyDocument, @NonNull AcrolinxSettings settings) throws AcrolinxException {
    String url = buildUrl(CHECK_RESOURCE_PATH, getHost(settings));
    HttpEntity<String> requestEntity = buildPostEntity(bodyDocument, settings);

    try {
      ResponseEntity<CheckPostResponseDocument> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, CheckPostResponseDocument.class);
      return responseEntity.getBody();
    } catch (HttpClientErrorException e) {
      throw new AcrolinxException("Failed to execute Acrolinx post REST call: " +e.getMessage() +  "(response body: '" + e.getResponseBodyAsString() + "')", e);
    }
  }

  @NonNull
  private String buildUrl(@NonNull String resourcePath, @NonNull String host) {
    return UriComponentsBuilder.newInstance()
                               .scheme(SCHEME)
                               .host(host)
                               .path(resourcePath)
                               .build()
                               .toString();
  }

  @NonNull
  private HttpEntity<String> buildGetEntity(@NonNull AcrolinxSettings acrolinxSettings) {
    HttpHeaders headers = buildAuthenticationHeaders(acrolinxSettings);
    return new HttpEntity<>(headers);
  }

  @NonNull
  private HttpEntity<String> buildPostEntity(@NonNull CheckBodyDocument body, @NonNull AcrolinxSettings acrolinxSettings) throws AcrolinxException {
    HttpHeaders headers = buildAuthenticationHeaders(acrolinxSettings);
    return new HttpEntity<>(toJson(body), headers);
  }

  @NonNull
  private HttpHeaders buildAuthenticationHeaders(@NonNull AcrolinxSettings settings) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
    headers.add(ACROLINX_CLIENT_HEADER, settings.getSignature().trim());
    headers.add(ACROLINX_CLIENT_AUTH_HEADER, settings.getAccessToken().trim());
    if(!StringUtils.isEmpty(settings.getClientLocale())) {
      headers.add(ACROLINX_CLIENT_LOCALE_HEADER, settings.getClientLocale().trim());
    }
    return headers;
  }

  @NonNull
  private static String toJson(@NonNull Object model) throws AcrolinxException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(model);
    } catch (IOException e) {
      throw new AcrolinxException("Failed to convert Body to json string", e);
    }
  }

  private String getHost(AcrolinxSettings settings) {
    return settings.getTenant() + ".acrolinx.cloud/api/v1/";
  }
}
