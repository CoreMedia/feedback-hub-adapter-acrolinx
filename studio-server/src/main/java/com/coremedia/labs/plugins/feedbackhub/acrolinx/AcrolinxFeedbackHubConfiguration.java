package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.api.AcrolinxService;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.InputStream;
import java.util.Properties;

@Configuration
@Import({CommonBeansForPluginsConfiguration.class})
public class AcrolinxFeedbackHubConfiguration {
  private static final Logger LOG = LoggerFactory.getLogger(AcrolinxFeedbackHubConfiguration.class);

  private String studioVersion;
  private String acrolinxServer;

  public AcrolinxFeedbackHubConfiguration() {
    loadPropertiesSet();
  }

  @Bean
  public AcrolinxService acrolinxService(@NonNull SitesService sitesService) {
    return new AcrolinxService(sitesService, studioVersion);
  }

  @Bean
  public AcrolinxCSPSettings acrolinxCSPSettings() {
    return new AcrolinxCSPSettings(acrolinxServer);
  }

  @Bean
  public AcrolinxFeedbackProviderFactory acrolinxContentFeedbackProviderFactory(@NonNull AcrolinxService acrolinxService) {
    return new AcrolinxFeedbackProviderFactory(acrolinxService);
  }

  //TODO this will be replaced in a future version with Spring properties, not yet provided by plugins
  private void loadPropertiesSet() {
    try (InputStream input = AcrolinxFeedbackHubConfiguration.class.getClassLoader().getResourceAsStream("configuration.properties")) {
      Properties prop = new Properties();

      if (input == null) {
        LOG.error("Unable to find configuration.properties for Acrolinx, using signature from content settings.");
        return;
      }

      //load a properties file from class path, inside static method
      prop.load(input);
      acrolinxServer = prop.getProperty("acrolinx.server");

      if (!StringUtils.isEmpty(acrolinxServer)) {
        LOG.info("Successfully loaded Acrolinx settings.");
      }

      studioVersion = prop.getProperty("studio.version");
    } catch (Exception e) {
      LOG.error("Failed to load Acrolinx configuration.properties: " + e.getMessage(), e);
    }

  }
}
