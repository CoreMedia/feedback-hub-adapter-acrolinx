package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.cmsEntities;

import java.util.List;
import java.util.Map;

public class TextQuality {
  private int score;

  private String scoreRatingColor;
  private int sentencesCount;
  private int wordsCount;
  private int issuesCount;
  private Map<String, List<TextIssue>> issuesCategories;

  public TextQuality(int score, String scoreRatingColor, int sentencesCount, int wordsCount, int issuesCount, Map<String, List<TextIssue>> issuesCategories) {
    this.score = score;
    this.scoreRatingColor = scoreRatingColor;
    this.sentencesCount = sentencesCount;
    this.wordsCount = wordsCount;
    this.issuesCount = issuesCount;
    this.issuesCategories = issuesCategories;
  }

  public int getScore() {
    return score;
  }

  public String getScoreRatingColor() {
    return scoreRatingColor;
  }

  public int getSentencesCount() {
    return sentencesCount;
  }

  public int getWordsCount() {
    return wordsCount;
  }

  public int getIssuesCount() {
    return issuesCount;
  }

  public Map<String, List<TextIssue>> getIssuesCategories() {
    return issuesCategories;
  }
}
