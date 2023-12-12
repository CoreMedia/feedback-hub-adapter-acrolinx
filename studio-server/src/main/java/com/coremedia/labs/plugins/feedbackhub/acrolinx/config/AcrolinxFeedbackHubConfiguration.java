package com.coremedia.labs.plugins.feedbackhub.acrolinx.config;

import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.AcrolinxCSPSettings;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.AcrolinxFeedbackProviderFactory;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.PluginConfigurationProperties;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.api.AcrolinxService;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@Import({CommonBeansForPluginsConfiguration.class})
@EnableConfigurationProperties({
    PluginConfigurationProperties.class,
    AcrolinxServerConfigurationProperties.class,
    AcrolinxClientConfigurationProperties.class
})
@PropertySource("classpath:acrolinx.properties")
public class AcrolinxFeedbackHubConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(AcrolinxFeedbackHubConfiguration.class);

  @Bean
  public AcrolinxService acrolinxService(@NonNull SitesService sitesService, @NonNull AcrolinxServerConfigurationProperties serverConfigurationProperties) {
    LOG.info("AcrolinxService: serverAddress: " + serverConfigurationProperties.getServerAddress());
    return new AcrolinxService(sitesService, serverConfigurationProperties.getSignature(), serverConfigurationProperties.getVersion());
  }

  @Bean
  public AcrolinxCSPSettings acrolinxCSPSettings(AcrolinxClientConfigurationProperties configurationProperties) {
    return new AcrolinxCSPSettings(asList(configurationProperties.getCsp().getScriptSrc()),
        asList(configurationProperties.getCsp().getStyleSrc()),
        asList(configurationProperties.getCsp().getImgSrc()),
        asList(configurationProperties.getCsp().getConnectSrc()),
        asList(configurationProperties.getCsp().getFrameSrc()),
        asList(configurationProperties.getCsp().getChildSrc()),
        asList(configurationProperties.getCsp().getFontSrc()));
  }

  @Bean
  public AcrolinxFeedbackProviderFactory acrolinxContentFeedbackProviderFactory(@NonNull AcrolinxService acrolinxService,
                                                                                @NonNull AcrolinxServerConfigurationProperties serverConfigurationProperties) {
    return new AcrolinxFeedbackProviderFactory(acrolinxService, serverConfigurationProperties.getSignature());
  }

  static List<String> asList(String value) {
    if (StringUtils.isNotBlank(value)) {
      return Arrays.asList(value.split(","));
    }
    return Collections.emptyList();
  }
}
