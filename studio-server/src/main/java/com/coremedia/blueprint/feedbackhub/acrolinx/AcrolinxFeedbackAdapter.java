package com.coremedia.blueprint.feedbackhub.acrolinx;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxException;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxService;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxSettings;
import com.coremedia.feedbackhub.adapter.FeedbackContext;
import com.coremedia.feedbackhub.adapter.text.TextFeedbackHubAdapter;
import com.coremedia.feedbackhub.items.FeedbackItem;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *
 */
@DefaultAnnotation(NonNull.class)
public class AcrolinxFeedbackAdapter implements TextFeedbackHubAdapter {
  private static final Logger LOG = LoggerFactory.getLogger(AcrolinxFeedbackAdapter.class);
  private final AcrolinxSettings settings;
  private final AcrolinxService service;

  AcrolinxFeedbackAdapter(AcrolinxSettings settings,
                          AcrolinxService service) {
    this.settings = settings;
    this.service = service;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> analyzeText(FeedbackContext context, Map<String, String> textProperties, @Nullable Locale locale) {
    try {
      List<FeedbackItem> items = Collections.emptyList();
      return CompletableFuture.completedFuture(items);
    } catch (AcrolinxException e) {
      return CompletableFuture.failedFuture(e);
    }
  }
}
