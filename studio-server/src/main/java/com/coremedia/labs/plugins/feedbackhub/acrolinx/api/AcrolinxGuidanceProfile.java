package com.coremedia.labs.plugins.feedbackhub.acrolinx.api;

import com.acrolinx.client.sdk.platform.GuidanceProfile;

/**
 *
 */
public class AcrolinxGuidanceProfile {

  private GuidanceProfile guidanceProfile;

  public AcrolinxGuidanceProfile(GuidanceProfile guidanceProfile) {
    this.guidanceProfile = guidanceProfile;
  }

  public String getName() {
    return guidanceProfile.getDisplayName();
  }

  public String getLanguage() {
    return guidanceProfile.getLanguage().getId();
  }

  public String getId() {
    return guidanceProfile.getId();
  }
}
