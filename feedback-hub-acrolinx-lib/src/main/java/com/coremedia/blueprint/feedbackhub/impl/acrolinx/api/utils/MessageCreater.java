package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.utils;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckGetResponseDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.IssueDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.MatchDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.SuggestionDocument;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MessageCreater {

  private static final String ISSUE_TYPE = "Issue Type : ";
  private static final String ISSUE_FOUND = "Issue Found On : ";
  private static final String ISSUE_DETAIL = "Issue Detail : ";
  private static final String ISSUE_GUIDANCE = "Issue Guidance : ";
  private static final String ISSUE_SUGGESTION = "Issue Suggestions : ";
  private static String NO_OF_ISSUES = "There are @ issues found.";

  @Nullable
  public String getCheckMessage(@Nullable CheckGetResponseDocument checkResponse) {

    if (checkResponse == null) {
      return null;
    }

    String message = NO_OF_ISSUES.replaceFirst("@", ""+checkResponse.getDataDocument().getCountDocument()
                                                                    .getIssues());
    String issueFound;

    List<IssueDocument> issues = checkResponse.getDataDocument().getIssueDocuments();

    if (issues == null || issues.isEmpty()) {
      return message;
    }

    List<IssueDocument> nonNullIssues = issues.stream().filter(Objects::nonNull).collect(Collectors.toList());

    for (IssueDocument issue : nonNullIssues) {
      message = message + ISSUE_TYPE + issue.getAspectId();
      if (null != issue.getPositionDocument()
          && issue.getPositionDocument().getMatches() != null
          && !issue.getPositionDocument().getMatches().isEmpty()) {
        issueFound = issue.getPositionDocument().getMatches()
                          .stream()
                          .map(MatchDocument::getExtractedPart)
                          .collect(Collectors.joining(","));
        message = message + ISSUE_FOUND + issueFound;
      }
      message = message + ISSUE_DETAIL + issue.getIssueName()
                + ISSUE_GUIDANCE + issue.getIssueGuidance();

      if (issue.getIssueSuggestions() != null && !issue.getIssueSuggestions().isEmpty()) {
        message = message + ISSUE_SUGGESTION
                  + getStringFromList(issue.getIssueSuggestions());
      }
    }
    return message;
  }

  private String getStringFromList(List<SuggestionDocument> suggestions) {

    List<String> returnString = suggestions.stream()
                                           .map(SuggestionDocument::getReplacements)
                                           .map(e -> String.join(",", e))
                                           .collect(Collectors.toList());

    return String.join(",", returnString);
  }
}
