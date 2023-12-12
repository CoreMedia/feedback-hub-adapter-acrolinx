package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.rest.security.csp.CSPSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AcrolinxCSPSettings implements CSPSettings {
  private static final Logger LOG = LoggerFactory.getLogger(AcrolinxCSPSettings.class);

  private final List<String> scriptSrc;
  private final List<String> styleSrc;
  private final List<String> imgSrc;
  private final List<String> connectSrc;
  private final List<String> frameSrc;
  private final List<String> childSrc;
  private final List<String> fontSrc;

  public AcrolinxCSPSettings(List<String> scriptSrc, List<String> styleSrc, List<String> imgSrc, List<String> connectSrc, List<String> frameSrc, List<String> childSrc, List<String> fontSrc) {
    this.scriptSrc = scriptSrc;
    this.styleSrc = styleSrc;
    this.imgSrc = imgSrc;
    this.connectSrc = connectSrc;
    this.frameSrc = frameSrc;
    this.childSrc = childSrc;
    this.fontSrc = fontSrc;

    LOG.info("AcrolinxCSPSettings: scriptSrc: " + scriptSrc);
    LOG.info("AcrolinxCSPSettings: styleSrc: " + styleSrc);
    LOG.info("AcrolinxCSPSettings: imgSrc: " + imgSrc);
    LOG.info("AcrolinxCSPSettings: connectSrc: " + connectSrc);
    LOG.info("AcrolinxCSPSettings: frameSrc: " + frameSrc);
    LOG.info("AcrolinxCSPSettings: childSrc: " + childSrc);
    LOG.info("AcrolinxCSPSettings: fontSrc: " + fontSrc);
  }

  @Override
  public List<String> getScriptSrc() {
    return scriptSrc;
  }

  @Override
  public List<String> getStyleSrc() {
    return this.styleSrc;
  }

  @Override
  public List<String> getFrameSrc() {
    return this.frameSrc;
  }

  @Override
  public List<String> getConnectSrc() {
    return this.connectSrc;
  }

  @Override
  public List<String> getFontSrc() {
    return this.fontSrc;
  }

  @Override
  public List<String> getImgSrc() {
    return this.imgSrc;
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
    return this.childSrc;
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
