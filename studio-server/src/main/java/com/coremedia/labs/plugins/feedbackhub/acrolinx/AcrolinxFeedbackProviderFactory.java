package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import com.coremedia.feedbackhub.provider.FeedbackProvider;
import com.coremedia.feedbackhub.provider.FeedbackProviderFactory;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.api.AcrolinxService;
import org.apache.commons.lang3.StringUtils;

public class AcrolinxFeedbackProviderFactory implements FeedbackProviderFactory<AcrolinxSettings> {
  public static final String TYPE = "acrolinx";
  private final AcrolinxService service;
  private final String clientSignature;

  public AcrolinxFeedbackProviderFactory(AcrolinxService service, String clientSignature) {
    this.service = service;
    this.clientSignature = clientSignature;
  }

  @Override
  public String getId() {
    return TYPE;
  }

  @Override
  public FeedbackProvider create(AcrolinxSettings settings) {
    String accessToken = settings.getServerAddress();
    if (StringUtils.isEmpty(accessToken)) {
      throw new FeedbackHubException("settings must provide an server", AcrolinxFeedbackHubErrorCode.SERVER_ADDRESS_NOT_SET);
    }

    String signature = settings.getClientSignature();
    if (StringUtils.isEmpty(signature) && StringUtils.isEmpty(clientSignature)) {
      throw new FeedbackHubException("settings must provide an client signature", AcrolinxFeedbackHubErrorCode.CLIENT_SIGNATURE_NOT_SET);
    }

    return new AcrolinxFeedbackProvider(service, settings);
  }
}
