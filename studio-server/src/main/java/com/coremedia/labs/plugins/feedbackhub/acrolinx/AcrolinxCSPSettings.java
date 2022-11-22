package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.rest.security.csp.CSPSettings;

import java.util.Arrays;
import java.util.List;

public class AcrolinxCSPSettings implements CSPSettings {

  private String acrolinxServer;

  AcrolinxCSPSettings(String acrolinxServer) {
    this.acrolinxServer = acrolinxServer;
  }

  @Override
  public List<String> getScriptSrc() {
    return Arrays.asList("'unsafe-eval'", "'self'", "data:", "http:", "https:",
            "app.eu.pendo.io",
            "pendo-eu-static.storage.googleapis.com",
            "cdn.eu.pendo.io",
            "pendo-eu-static-5227652489019392.storage.googleapis.com",
            "data.eu.pendo.io",
            "'sha256-uvjFzlIG+c9vTvlPjd4XprgNwAHtbxCBrl7kg8AOP3A='",
            "'sha256-czdU7T58bnMmn54YmKphBkPUShRlMF5Naf66fSAEVF8='");
  }

  @Override
  public List<String> getStyleSrc() {
    return Arrays.asList("'unsafe-inline'", "'self'", "data:", "http:", "https://" + acrolinxServer + "/");
  }

  @Override
  public List<String> getFrameSrc() {
    return CSPSettings.super.getFrameSrc();
  }

  @Override
  public List<String> getConnectSrc() {
    return Arrays.asList("'self'", "https://" + acrolinxServer + "/");
  }

  @Override
  public List<String> getFontSrc() {
    return Arrays.asList("'self'", "data:", "http:", "https:", "https://unpkg.com/");
  }

  @Override
  public List<String> getImgSrc() {
    return Arrays.asList("data:", "http:", "https:");
  }

  @Override
  public List<String> getMediaSrc() {
    return CSPSettings.super.getMediaSrc();
  }

  @Override
  public List<String> getManifestSrc() {
    return CSPSettings.super.getManifestSrc();
  }

  @Override
  public List<String> getObjectSrc() {
    return CSPSettings.super.getObjectSrc();
  }

  @Override
  public List<String> getChildSrc() {
    return CSPSettings.super.getChildSrc();
  }

  @Override
  public List<String> getReportUri() {
    return CSPSettings.super.getReportUri();
  }

  @Override
  public List<String> getFrameAncestors() {
    return CSPSettings.super.getFrameAncestors();
  }
}
