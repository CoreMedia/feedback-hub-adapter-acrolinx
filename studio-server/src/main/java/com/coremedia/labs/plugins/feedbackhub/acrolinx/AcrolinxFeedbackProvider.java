package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.FeedbackItemDefaultCollections;
import com.coremedia.feedbackhub.adapter.FeedbackContext;
import com.coremedia.feedbackhub.items.FeedbackItem;
import com.coremedia.feedbackhub.items.FeedbackItemFactory;
import com.coremedia.feedbackhub.items.LabelFeedbackItem;
import com.coremedia.feedbackhub.items.LabelFeedbackItemBuilder;
import com.coremedia.feedbackhub.provider.FeedbackProvider;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.api.AcrolinxGuidanceProfile;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.api.AcrolinxService;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.items.AcrolinxSidebarFeedbackItem;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *
 */
@DefaultAnnotation(NonNull.class)
public class AcrolinxFeedbackProvider implements FeedbackProvider {
  private final AcrolinxService acrolinxService;
  private final AcrolinxSettings settings;

  AcrolinxFeedbackProvider(AcrolinxService acrolinxService, AcrolinxSettings settings) {
    this.acrolinxService = acrolinxService;
    this.settings = settings;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> provideFeedback(FeedbackContext feedbackContext) {
    List<FeedbackItem> items = new ArrayList<>();
    String host = settings.getServerAddress();
    String clientSignature = acrolinxService.getClientSignature(settings);
    Content content = (Content) feedbackContext.getEntity();
    String profileId = null;
    AcrolinxGuidanceProfile guidanceProfile = acrolinxService.getGuidanceProfileFor(settings, content);

    //if a profile was found, provide a label at the top of the sidebar
    if (guidanceProfile != null) {
      profileId = guidanceProfile.getId();

      LabelFeedbackItem guidanceLabel = LabelFeedbackItemBuilder.builder()
              .withCollection(FeedbackItemDefaultCollections.header.name())
              .withLabel("acrolinx_guidance_profile", guidanceProfile.getName())
              .build();

      items.add(guidanceLabel);
    }

    items.add(FeedbackItemFactory.createFeedbackLink("https://" + host + "/dashboard.html"));
    items.add(new AcrolinxSidebarFeedbackItem(host, clientSignature, asList(settings.getPropertyNames()), profileId));

    return CompletableFuture.completedFuture(items);
  }

  /**
   * Converts the CSV value into a list
   *
   * @param propertyNames the CSV string which contains the property names
   */
  private List<String> asList(String propertyNames) {
    if (StringUtils.isEmpty(propertyNames)) {
      return Collections.emptyList();
    }

    if (propertyNames.contains(",")) {
      return Arrays.asList(propertyNames.split(","));
    }

    return Collections.singletonList(propertyNames);
  }
}
