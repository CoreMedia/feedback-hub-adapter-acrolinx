package com.coremedia.blueprint.feedbackhub.acrolinx.items;

import com.coremedia.blueprint.searchmetrics.documents.ContentOptResult;
import com.coremedia.feedbackhub.items.FeedbackItem;

import java.util.List;

/**
 *
 */
public class KeywordListFeedbackItem implements FeedbackItem {
  public static final String MUST_HAVE = "MUST_HAVE";
  public static final String RELEVANCE = "RELEVANCE";
  public static final String ADDITIONAL = "ADDITIONAL";

  private final String collection;
  private final String title;
  private final List<ContentOptResult> contentOptResults;
  private final String keywordType;
  private final String help;

  public KeywordListFeedbackItem(String collection, String title, List<ContentOptResult> contentOptResults, String keywordType, String help) {
    this.collection = collection;
    this.title = title;
    this.contentOptResults = contentOptResults;
    this.keywordType = keywordType;
    this.help = help;
  }

  @Override
  public String getTitle() {
    return title;
  }

  public List<ContentOptResult> getContentOptResults() {
    return contentOptResults;
  }

  public String getKeywordType() {
    return keywordType;
  }

  public String getHelp() {
    return help;
  }

  @Override
  public String getCollection() {
    return this.collection;
  }

  @Override
  public String getType() {
    return "searchmetricsKeywordList";
  }
}
