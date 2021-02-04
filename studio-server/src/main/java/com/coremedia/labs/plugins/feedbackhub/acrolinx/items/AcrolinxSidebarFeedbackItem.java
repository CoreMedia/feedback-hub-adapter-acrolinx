package com.coremedia.labs.plugins.feedbackhub.acrolinx.items;

import com.coremedia.feedbackhub.items.FeedbackItem;

import java.util.List;

/**
 * This is just used as a placeholder for the Acrolinx sidebar.
 */
public class AcrolinxSidebarFeedbackItem implements FeedbackItem {

  private final String serverAddress;
  private final String clientSignature;

  private final List<String> properties;

  public AcrolinxSidebarFeedbackItem(String serverAddress, String clientSignature, List<String> properties) {
    this.serverAddress = serverAddress;
    this.clientSignature = clientSignature;
    this.properties = properties;
  }

  public List<String> getProperties() {
    return properties;
  }

  public String getServerAddress() {
    return serverAddress;
  }

  public String getClientSignature() {
    return clientSignature;
  }

  public String getType() {
    return "acrolinxSidebar";
  }
}
