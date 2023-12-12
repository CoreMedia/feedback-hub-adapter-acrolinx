package com.coremedia.labs.plugins.feedbackhub.acrolinx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static com.coremedia.labs.plugins.feedbackhub.acrolinx.PluginConfiguration.PLUGIN_ID;

@ConfigurationProperties(prefix = "plugins." + PLUGIN_ID + ".client")
public class AcrolinxClientConfigurationProperties {

  @NestedConfigurationProperty
  private final AcrolinxClientConfigurationProperties.Csp cspConfig = new AcrolinxClientConfigurationProperties.Csp();

  public AcrolinxClientConfigurationProperties.Csp getCsp() {
    return cspConfig;
  }

  public static class Csp {
    private String scriptSrc = null;
    private String styleSrc = null;
    private String imgSrc = null;
    private String connectSrc = null;
    private String frameSrc = null;
    private String childSrc = null;
    private String fontSrc = null;

    public String getScriptSrc() {
      return scriptSrc;
    }

    public void setScriptSrc(String scriptSrc) {
      this.scriptSrc = scriptSrc;
    }

    public String getStyleSrc() {
      return styleSrc;
    }

    public void setStyleSrc(String styleSrc) {
      this.styleSrc = styleSrc;
    }

    public String getImgSrc() {
      return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
      this.imgSrc = imgSrc;
    }

    public String getConnectSrc() {
      return connectSrc;
    }

    public void setConnectSrc(String connectSrc) {
      this.connectSrc = connectSrc;
    }

    public String getFrameSrc() {
      return frameSrc;
    }

    public void setFrameSrc(String frameSrc) {
      this.frameSrc = frameSrc;
    }

    public String getChildSrc() {
      return childSrc;
    }

    public void setChildSrc(String childSrc) {
      this.childSrc = childSrc;
    }

    public String getFontSrc() {
      return fontSrc;
    }

    public void setFontSrc(String fontSrc) {
      this.fontSrc = fontSrc;
    }
  }
}
