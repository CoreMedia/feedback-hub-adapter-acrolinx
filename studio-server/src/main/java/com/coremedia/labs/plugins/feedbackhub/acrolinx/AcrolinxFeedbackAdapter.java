package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import com.coremedia.feedbackhub.adapter.FeedbackContext;
import com.coremedia.feedbackhub.adapter.text.TextFeedbackHubAdapter;
import com.coremedia.feedbackhub.items.FeedbackItem;
import com.coremedia.feedbackhub.items.FeedbackItemFactory;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.items.AcrolinxSidebarFeedbackItem;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *
 */
@DefaultAnnotation(NonNull.class)
public class AcrolinxFeedbackAdapter implements TextFeedbackHubAdapter {
  private final AcrolinxSettings settings;

  AcrolinxFeedbackAdapter(AcrolinxSettings settings) {
    this.settings = settings;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> analyzeText(FeedbackContext context, Map<String, String> textProperties, @Nullable Locale locale) {
    List<FeedbackItem> items = new ArrayList<>();
    String host= settings.getServerAddress();
    String clientSignature= settings.getClientSignature();
    items.add(FeedbackItemFactory.createFeedbackLink("https://" + host + "/dashboard.html"));
    items.add(new AcrolinxSidebarFeedbackItem(host, clientSignature));
    return CompletableFuture.completedFuture(items);
  }
}
