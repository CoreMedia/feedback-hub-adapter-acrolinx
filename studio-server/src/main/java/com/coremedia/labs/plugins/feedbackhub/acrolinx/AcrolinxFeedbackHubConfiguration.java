package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonBeansForPluginsConfiguration.class})
public class AcrolinxFeedbackHubConfiguration {

  @Bean
  public AcrolinxFeedbackAdapterFactory acrolinxContentFeedbackProviderFactory() {
    return new AcrolinxFeedbackAdapterFactory();
  }
}
