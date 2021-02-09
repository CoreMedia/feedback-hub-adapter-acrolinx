package com.coremedia.labs.plugins.feedbackhub.acrolinx.api;

import com.coremedia.cache.Cache;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cap.struct.Struct;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.AcrolinxSettings;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Service for accessing the Acrolinx SDK.
 */
public class AcrolinxService {

  private Cache cache;
  private SitesService sitesService;

  public AcrolinxService(SitesService sitesService) {
    this.cache = new Cache("acrolinx");
    this.sitesService = sitesService;
  }

  /**
   * Returns the list of guidance profiles from Acrolinx.
   */
  @NonNull
  public List<AcrolinxGuidanceProfile> getGuidanceProfiles(@NonNull AcrolinxSettings settings) {
    GuidanceProfilesCacheKey key = new GuidanceProfilesCacheKey(settings.getServerAddress(), settings.getAccessToken(), settings.getClientSignature());
    return cache.get(key);
  }

  @Nullable
  public AcrolinxGuidanceProfile getGuidanceProfileFor(@NonNull AcrolinxSettings acrolinxSettings, @NonNull Content content) {
    List<AcrolinxGuidanceProfile> guidanceProfiles = getGuidanceProfiles(acrolinxSettings);
    Site site = sitesService.getContentSiteAspect(content).getSite();
    Struct profileMapping = ((Struct) acrolinxSettings.getContent()).getStruct("profileMapping");
    AcrolinxGuidanceProfile guidanceProfile = null;

    if (site != null && profileMapping != null) {
      Map<String, Object> profilesMap = profileMapping.toNestedMaps();

      //try to find a mapping for the given site
      if (profilesMap.containsKey(site.getId())) {
        String profileIdOrName = (String) profilesMap.get(site.getId());
        guidanceProfile = findProfileByIdOrName(profileIdOrName, guidanceProfiles);
      }

      //try language next
      if (guidanceProfile == null) {
        String language = site.getLocale().getLanguage();
        guidanceProfile = findProfileByLanguage(language, guidanceProfiles);
      }

      //check if there is an overall default profile
      if (guidanceProfile == null) {
        if (profilesMap.containsKey("default")) {
          String profileIdOrName = (String) profilesMap.get("default");
          guidanceProfile = findProfileByIdOrName(profileIdOrName, guidanceProfiles);
        }
      }
    }

    return guidanceProfile;
  }

  private AcrolinxGuidanceProfile findProfileByIdOrName(String id, List<AcrolinxGuidanceProfile> guidanceProfiles) {
    for (AcrolinxGuidanceProfile guidanceProfile : guidanceProfiles) {
      if (guidanceProfile.getName().equals(id) || guidanceProfile.getId().equals(id)) {
        return guidanceProfile;
      }
    }
    return null;
  }

  private AcrolinxGuidanceProfile findProfileByLanguage(String language, List<AcrolinxGuidanceProfile> guidanceProfiles) {
    for (AcrolinxGuidanceProfile guidanceProfile : guidanceProfiles) {
      if (guidanceProfile.getLanguage().equals(language)) {
        return guidanceProfile;
      }
    }
    return null;
  }
}
