package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.cms.common.plugins.beans_for_plugins.CommonBeansForPluginsConfiguration;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.config.AcrolinxFeedbackHubConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.coremedia.labs.plugins.feedbackhub.acrolinx.PluginConfiguration.PLUGIN_ID;

@Configuration(proxyBeanMethods = false)
@Import({CommonBeansForPluginsConfiguration.class,
        AcrolinxFeedbackHubConfiguration.class,
})
@EnableConfigurationProperties(PluginConfigurationProperties.class)
@ConditionalOnProperty(name = "plugins." + PLUGIN_ID + ".enabled", havingValue = "true", matchIfMissing = true)
public class PluginConfiguration {
  public static final String PLUGIN_ID = "studio-server.feedback-hub-adapter-acrolinx";
}
