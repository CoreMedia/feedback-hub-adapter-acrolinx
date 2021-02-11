package com.coremedia.labs.plugins.feedbackhub.acrolinx.api;

import com.coremedia.cache.Cache;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.cap.struct.Struct;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.AcrolinxSettings;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Service for accessing the Acrolinx SDK.
 */
public class AcrolinxService {
  public static final String ACROLINX_CACHE = "acrolinx";

  public static final String PROFILE_MAPPING_PROPERTY = "profileMapping";
  public static final String DEFAULT_PROFILE_MAPPING_KEY = "default";

  private final Cache cache;
  private final SitesService sitesService;

  public AcrolinxService(SitesService sitesService) {
    this.cache = new Cache(ACROLINX_CACHE);
    this.cache.setCapacity(Object.class.getName(), 20);
    this.sitesService = sitesService;
  }

  /**
   * Returns the list of guidance profiles from Acrolinx if an access token
   * was provided with the settings.
   * Otherwise the user has to choose the profile manually.
   */
  @NonNull
  public List<AcrolinxGuidanceProfile> getGuidanceProfiles(@NonNull AcrolinxSettings settings) {
    if(!StringUtils.isEmpty(settings.getAccessToken())) {
      GuidanceProfilesCacheKey key = new GuidanceProfilesCacheKey(settings.getServerAddress(), settings.getAccessToken(), settings.getClientSignature());
      return cache.get(key);
    }
    return Collections.emptyList();
  }

  /**
   * Tries to resole a guidance profile for the given content.
   *
   * @param acrolinxSettings the settings to connect the Acrolinx SDK with
   * @param content          the content to resolve the guidance profile for
   */
  @Nullable
  public AcrolinxGuidanceProfile getGuidanceProfileFor(@NonNull AcrolinxSettings acrolinxSettings, @NonNull Content content) {
    List<AcrolinxGuidanceProfile> guidanceProfiles = getGuidanceProfiles(acrolinxSettings);
    Site site = sitesService.getContentSiteAspect(content).getSite();

    //we use the hidden "getContent()" proxy method to get the actual settings struct to get the mapping struct
    Struct settingsStruct = (Struct) acrolinxSettings.getContent();
    if (!settingsStruct.toNestedMaps().containsKey(PROFILE_MAPPING_PROPERTY)) {
      return null;
    }

    Struct profileMapping = (settingsStruct).getStruct(PROFILE_MAPPING_PROPERTY);
    if (site != null && profileMapping != null) {
      return getAcrolinxGuidanceProfile(guidanceProfiles, site, profileMapping);
    }

    return null;
  }

  /**
   * The profile mapping is is a key/value list of CoreMedia Side Ids/Language to Acrolinx Profile Name/Id.
   * If only one profile is supported, this one can be mapped through the key value "default".
   *
   * @param guidanceProfiles the list of Acrolinx guidance profiles
   * @param site             the site of the content to analyze
   * @param profileMapping   the profile mapping struct
   * @return the guidance profile for the content or null if not suitable mapping was found
   */
  @Nullable
  private AcrolinxGuidanceProfile getAcrolinxGuidanceProfile(List<AcrolinxGuidanceProfile> guidanceProfiles, Site site, Struct profileMapping) {
    Map<String, Object> profilesMap = profileMapping.toNestedMaps();
    AcrolinxGuidanceProfile guidanceProfile = null;

    //try to find a mapping for the given site
    if (profilesMap.containsKey(site.getId())) {
      String profileIdOrName = (String) profilesMap.get(site.getId());
      if (!StringUtils.isEmpty(profileIdOrName)) {
        guidanceProfile = findProfileByIdOrName(profileIdOrName, guidanceProfiles);
      }
    }

    //try language next
    if (guidanceProfile == null) {
      String language = site.getLocale().getLanguage();
      guidanceProfile = findProfileByLanguage(language, guidanceProfiles);
    }

    //check if there is an overall default profile
    if (guidanceProfile == null && profilesMap.containsKey(DEFAULT_PROFILE_MAPPING_KEY)) {
      String profileIdOrName = (String) profilesMap.get(DEFAULT_PROFILE_MAPPING_KEY);
      if (!StringUtils.isEmpty(profileIdOrName)) {
        guidanceProfile = findProfileByIdOrName(profileIdOrName, guidanceProfiles);
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
