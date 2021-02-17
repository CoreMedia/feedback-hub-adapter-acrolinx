package com.coremedia.labs.plugins.feedbackhub.acrolinx.api;

import com.acrolinx.client.sdk.AccessToken;
import com.acrolinx.client.sdk.AcrolinxEndpoint;
import com.acrolinx.client.sdk.platform.Capabilities;
import com.acrolinx.client.sdk.platform.CheckingCapabilities;
import com.acrolinx.client.sdk.platform.GuidanceProfile;
import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Time based caching for guidance profiles
 */
public class GuidanceProfilesCacheKey extends CacheKey<List<AcrolinxGuidanceProfile>> {
  public static final String ENDPOINT_LOCALE = "en";
  public static final int CACHE_DURATION_MINUTES = 5;

  private final String serverAddress;
  private final String token;
  private final String signature;
  private final String studioVersion;

  public GuidanceProfilesCacheKey(String url, String token, String signature, String studioVersion) {
    this.token = token;
    this.signature = signature;
    this.studioVersion = studioVersion;

    if(!url.startsWith("http")) {
      url = "https://" + url;
    }
    this.serverAddress = url;
  }

  @Override
  public List<AcrolinxGuidanceProfile> evaluate(Cache cache) throws Exception {
    URI realAcrolinxURL = new URI(serverAddress);
    AccessToken accessToken = new AccessToken(token);

    AcrolinxEndpoint endpoint = new AcrolinxEndpoint(realAcrolinxURL, signature, studioVersion, ENDPOINT_LOCALE);
    Capabilities capabilities = endpoint.getCapabilities(accessToken);
    CheckingCapabilities checkingCapabilities = capabilities.getCheckingCapabilities();
    List<GuidanceProfile> guidanceProfiles = checkingCapabilities.getGuidanceProfiles();

    Cache.cacheFor(CACHE_DURATION_MINUTES, TimeUnit.MINUTES);

    return guidanceProfiles.stream().map(AcrolinxGuidanceProfile::new).collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GuidanceProfilesCacheKey that = (GuidanceProfilesCacheKey) o;
    return serverAddress.equals(that.serverAddress)
            && token.equals(that.token)
            && signature.equals(that.signature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serverAddress, token, signature);
  }
}
