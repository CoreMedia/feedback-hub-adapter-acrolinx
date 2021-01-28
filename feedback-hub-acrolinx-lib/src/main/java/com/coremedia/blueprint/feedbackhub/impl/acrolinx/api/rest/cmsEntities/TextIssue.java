package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.cmsEntities;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public class TextIssue {
  private String descriptiveIssueText;
  private String problematicPart;
  private String problematicPartContext;
  private String displaySurface;
  private List<String> suggestions;
  private int problematicPartStart;
  private int problematicPartEnd;

  public TextIssue(@NonNull String descriptiveIssueText,
                   @NonNull String problematicPart,
                   @NonNull String problematicPartContext) {
    this.descriptiveIssueText = descriptiveIssueText;
    this.problematicPart = problematicPart;
    this.problematicPartContext = problematicPartContext;
  }

  public TextIssue(@NonNull String descriptiveIssueText,
                   @NonNull String displaySurface,
                   @NonNull String problematicPart,
                   @NonNull String problematicPartContext,
                   @Nullable List<String> suggestions,
                   int problematicPartStart,
                   int problematicPartEnd) {
    this.descriptiveIssueText = descriptiveIssueText;
    this.displaySurface = displaySurface;
    this.problematicPart = problematicPart;
    this.problematicPartContext = problematicPartContext;
    this.suggestions = suggestions;
    this.problematicPartStart = problematicPartStart;
    this.problematicPartEnd = problematicPartEnd;
  }

  public String getDescriptiveIssueText() {
    return descriptiveIssueText;
  }

  public String getProblematicPart() {
    return problematicPart;
  }

  public String getProblematicPartContext() {
    return problematicPartContext;
  }

  @Nullable
  public List<String> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(@Nullable List<String> suggestions) {
    this.suggestions = suggestions;
  }

  public int getProblematicPartStart() {
    return problematicPartStart;
  }

  public int getProblematicPartEnd() {
    return problematicPartEnd;
  }

  public String getDisplaySurface() {
    return displaySurface;
  }
}
