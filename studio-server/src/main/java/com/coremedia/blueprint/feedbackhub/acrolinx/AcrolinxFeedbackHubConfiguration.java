package com.coremedia.blueprint.feedbackhub.acrolinx;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.AcrolinxFeedbackConfiguration;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxService;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonBeansForPluginsConfiguration.class, AcrolinxFeedbackConfiguration.class})
public class AcrolinxFeedbackHubConfiguration {

  @Bean
  public AcrolinxFeedbackAdapterFactory searchmetricsContentFeedbackProviderFactory(@NonNull AcrolinxService acrolinxService) {
    return new AcrolinxFeedbackAdapterFactory(acrolinxService);
  }
}
