package com.coremedia.labs.plugins.feedbackhub.acrolinx.items;

import com.coremedia.feedbackhub.items.FeedbackItem;

/**
 * This is just used as a placeholder for the Acrolinx sidebar.
 */
public class AcrolinxSidebarFeedbackItem implements FeedbackItem {

  private final String host;
  private final String clientSignature;

  public AcrolinxSidebarFeedbackItem(String host, String clientSignature) {
    this.host = host;
    this.clientSignature = clientSignature;
  }

  public String getServerAddress() {
    return host;
  }

  public String getClientSignature() {
    return clientSignature;
  }

  public String getType() {
    return "acrolinxSidebar";
  }
}
