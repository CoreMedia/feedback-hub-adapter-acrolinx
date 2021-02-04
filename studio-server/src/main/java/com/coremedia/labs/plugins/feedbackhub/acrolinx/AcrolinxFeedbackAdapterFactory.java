package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.feedbackhub.adapter.FeedbackHubAdapter;
import com.coremedia.feedbackhub.adapter.FeedbackHubAdapterFactory;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import org.apache.commons.lang3.StringUtils;

public class AcrolinxFeedbackAdapterFactory implements FeedbackHubAdapterFactory<AcrolinxSettings> {
  public static final String TYPE = "acrolinx";

  public AcrolinxFeedbackAdapterFactory() {
  }

  @Override
  public String getId() {
    return TYPE;
  }

  @Override
  public FeedbackHubAdapter create(AcrolinxSettings settings) {
    String accessToken = settings.getServerAddress();
    if (StringUtils.isEmpty(accessToken)) {
      throw new FeedbackHubException("settings must provide an server", AcrolinxFeedbackHubErrorCode.SERVER_ADDRESS_NOT_SET);
    }

    String signature = settings.getClientSignature();
    if (StringUtils.isEmpty(signature)) {
      throw new FeedbackHubException("settings must provide an client signature", AcrolinxFeedbackHubErrorCode.CLIENT_SIGNATURE_NOT_SET);
    }

    return new AcrolinxFeedbackAdapter(settings);
  }
}
