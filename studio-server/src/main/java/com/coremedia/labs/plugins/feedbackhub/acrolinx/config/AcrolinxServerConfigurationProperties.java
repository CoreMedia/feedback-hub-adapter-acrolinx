package com.coremedia.labs.plugins.feedbackhub.acrolinx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.coremedia.labs.plugins.feedbackhub.acrolinx.PluginConfiguration.PLUGIN_ID;

@ConfigurationProperties(prefix = "plugins." + PLUGIN_ID + ".server")
public class AcrolinxServerConfigurationProperties {

  private String signature;
  private String version;
  private String serverAddress;

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getServerAddress() {
    return serverAddress;
  }

  public void setServerAddress(String serverAddress) {
    this.serverAddress = serverAddress;
  }
}
