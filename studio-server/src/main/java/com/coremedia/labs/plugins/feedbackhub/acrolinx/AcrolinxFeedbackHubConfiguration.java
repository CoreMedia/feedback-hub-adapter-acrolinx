package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.api.AcrolinxService;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonBeansForPluginsConfiguration.class})
public class AcrolinxFeedbackHubConfiguration {

  @Bean
  public AcrolinxService acrolinxService(@NonNull SitesService sitesService) {
    return new AcrolinxService(sitesService);
  }

  @Bean
  public AcrolinxFeedbackProviderFactory acrolinxContentFeedbackProviderFactory(@NonNull AcrolinxService acrolinxService) {
    return new AcrolinxFeedbackProviderFactory(acrolinxService);
  }
}
