package com.coremedia.blueprint.feedbackhub.acrolinx;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxService;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxSettings;
import com.coremedia.blueprint.searchmetrics.SearchmetricsSettings;
import com.coremedia.feedbackhub.adapter.FeedbackHubAdapter;
import com.coremedia.feedbackhub.adapter.FeedbackHubAdapterFactory;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;

public class AcrolinxFeedbackAdapterFactory implements FeedbackHubAdapterFactory<AcrolinxSettings> {
  public static final String TYPE = "acrolinx";
  private AcrolinxService service;

  public AcrolinxFeedbackAdapterFactory(@NonNull AcrolinxService acrolinxService) {
    this.service = service;
  }

  @Override
  public String getId() {
    return TYPE;
  }

  @Override
  public FeedbackHubAdapter create(AcrolinxSettings settings) {
    String accessToken = settings.getAccessToken();
    if (StringUtils.isEmpty(accessToken)) {
      throw new FeedbackHubException("settings must provide an accessToken", AcrolinxFeedbackHubErrorCode.ACCESS_TOKEN_NOT_SET);
    }

    String signature = settings.getSignature();
    if (StringUtils.isEmpty(signature)) {
      throw new FeedbackHubException("settings must provide an signature", AcrolinxFeedbackHubErrorCode.SIGNATURE_NOT_SET);
    }

    String tenant = settings.getTenant();
    if (StringUtils.isEmpty(tenant)) {
      throw new FeedbackHubException("settings must provide an tenant", AcrolinxFeedbackHubErrorCode.TENANT_NOT_SET);
    }

    return new AcrolinxFeedbackAdapter(settings, service);
  }
}
