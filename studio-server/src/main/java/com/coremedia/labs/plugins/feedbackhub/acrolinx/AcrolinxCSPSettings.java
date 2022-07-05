package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.rest.security.util.CSPSettings;

import java.util.Arrays;
import java.util.List;

public class AcrolinxCSPSettings implements CSPSettings {

  private final String acrolinxServer;

  AcrolinxCSPSettings(String acrolinxServer) {
    this.acrolinxServer = acrolinxServer;
  }

  @Override
  public List<String> getScriptSrc() {
    return Arrays.asList("'unsafe-eval'", "'unsafe-inline'", "'self'", "data:", "http:", "https:", "https://unpkg.com/");
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
    return Arrays.asList("'self'","https://" + acrolinxServer + "/");
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
  public List<String> getReportUri() {
    return CSPSettings.super.getReportUri();
  }

  @Override
  public List<String> getFrameAncestors() {
    return CSPSettings.super.getFrameAncestors();
  }
}
