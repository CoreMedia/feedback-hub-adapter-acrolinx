package com.coremedia.labs.plugins.feedbackhub.acrolinx.items;

import com.coremedia.feedbackhub.FeedbackItemDefaultCollections;
import com.coremedia.feedbackhub.items.FeedbackItem;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;

/**
 * This is just used as a placeholder for the Acrolinx sidebar.
 */
public class AcrolinxSidebarFeedbackItem implements FeedbackItem {

  private final String serverAddress;
  private final String clientSignature;
  private final String profileId;

  private final List<String> properties;

  public AcrolinxSidebarFeedbackItem(String serverAddress, String clientSignature, List<String> properties, String profileId) {
    this.serverAddress = serverAddress;
    this.clientSignature = clientSignature;
    this.properties = properties;
    this.profileId = profileId;
  }

  @Nullable
  @Override
  public String getCollection() {
    return FeedbackItemDefaultCollections.HEADER;
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

  public String getProfileId() {
    return profileId;
  }

  @Override
  public String getType() {
    return "acrolinxSidebar";
  }
}
