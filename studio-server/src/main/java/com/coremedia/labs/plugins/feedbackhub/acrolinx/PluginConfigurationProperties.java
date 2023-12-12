package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "plugins." + PluginConfiguration.PLUGIN_ID)
public class PluginConfigurationProperties {
}
