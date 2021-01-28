package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.cmsEntities.TextIssue;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.cmsEntities.TextQuality;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.AspectDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckBodyDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckGetResponseDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckOptionsDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.DataDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.IssueDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.MatchDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.PositionDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.SuggestionDocument;
import com.coremedia.cache.Cache;
import com.coremedia.cache.CacheKey;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AcrolinxCheckQualityCacheKey extends CacheKey<TextQuality> {

  private AcrolinxRestConnector acrolinxRestConnector;
  private String text;
  private CheckOptionsDocument checkOptionsDocument;
  private AcrolinxSettings settings;

  AcrolinxCheckQualityCacheKey(@NonNull AcrolinxRestConnector acrolinxRestConnector,
                               @NonNull String text,
                               @NonNull CheckOptionsDocument checkOptionsDocument,
                               @NonNull AcrolinxSettings settings) {
    this.acrolinxRestConnector = acrolinxRestConnector;
    this.text = text;
    this.checkOptionsDocument = checkOptionsDocument;
    this.settings = settings;
  }


  @Override
  public TextQuality evaluate(Cache cache) throws Exception {
    CheckBodyDocument bodyDocument = new CheckBodyDocument();
    bodyDocument.setCheckOptionsDocument(checkOptionsDocument);
    bodyDocument.setContent(text);

    CheckGetResponseDocument checkGetResponseDocument = acrolinxRestConnector.checkText(bodyDocument, settings);
    if (checkGetResponseDocument == null) {
      return null;
    }

    DataDocument dataDocument = checkGetResponseDocument.getDataDocument();
    if (dataDocument == null) {
      return null;
    }

    int sentenceCount = extractSentenceCount(dataDocument);
    int wordCount = extractWordCount(dataDocument);
    int issuesCount = extractIssuesCount(dataDocument);
    int score = extractScore(dataDocument);
    String scoreRatingColor = extractScoreRatingColor(dataDocument);
    Map<String, List<TextIssue>> issues = extractIssues(text, dataDocument);

    return new TextQuality(score, scoreRatingColor, sentenceCount, wordCount, issuesCount, issues);
  }

  @Nullable
  private TextIssue toTextIssue(@NonNull String detailedText, @NonNull IssueDocument issueDocument) {
    PositionDocument positionDocument = issueDocument.getPositionDocument();
    List<MatchDocument> matches = positionDocument.getMatches();

    if (matches.isEmpty()) {
      return null;
    }

    String issueDisplayName = issueDocument.getIssueName();
    String problematicPart = extractProblematicPart(matches);
    int problematicPartStart = extractProblematicPartStart(matches);
    int problematicPartEnd = extractProblematicPartEnd(matches);
    String problematicPartContext = extractContext(detailedText, matches.iterator().next());
    List<String> suggestions = extractSuggestions(issueDocument);
    return new TextIssue(issueDisplayName, issueDocument.getDisplaySurface(), problematicPart, problematicPartContext, suggestions, problematicPartStart, problematicPartEnd);
  }

  private int extractProblematicPartEnd(List<MatchDocument> matches) {
    if (matches.size() == 0) {
      return -1;
    }

    int last = matches.size() - 1;
    return matches.get(last).getExtractedEnd();
  }

  private int extractProblematicPartStart(@NonNull List<MatchDocument> matches) {
    if (matches.size() == 0) {
      return -1;
    }

    int startsAfter = matches.get(0).getExtractedBegin();
    return startsAfter + 1; // first character
  }

  @Nullable
  private List<String> extractSuggestions(@NonNull IssueDocument issueDocument) {
    List<SuggestionDocument> issueSuggestions = issueDocument.getIssueSuggestions();
    if (issueSuggestions == null || issueSuggestions.isEmpty()) {
      return null;
    }

    return issueSuggestions.stream().map(this::toSuggestion).collect(Collectors.toList());
  }


  @NonNull
  private Map<String, String> extractAspectMap(@NonNull DataDocument dataDocument) {
    Map<String, String> result = new HashMap<>();

    List<AspectDocument> aspectDocuments = dataDocument.getAspectsDocuments();
    for (AspectDocument aspectDocument : aspectDocuments) {
      result.put(aspectDocument.getAspectdId(), aspectDocument.getAspectName());
    }
    return result;
  }

  @Nullable
  private String toSuggestion(@NonNull SuggestionDocument suggestionDocument) {
    List<String> replacements = suggestionDocument.getReplacements();
    if (replacements == null || replacements.isEmpty()) {
      return null;
    }

    return replacements.stream()
            .filter(s -> s.trim().length() > 0)
            .findFirst()
            .orElse(null);
  }

  @NonNull
  private String extractProblematicPart(@NonNull List<MatchDocument> matches) {
    return matches.stream().map(MatchDocument::getExtractedPart).collect(Collectors.joining(" "));
  }

  @NonNull
  private String extractContext(@NonNull String detailedText, @NonNull MatchDocument match) {
    int originalBegin = match.getOriginalBegin();
    int originalEnd = match.getOriginalEnd();
    int startSentence = searchStart(detailedText, originalBegin);
    int endSentence = searchEnd(detailedText, originalEnd);
    return detailedText.substring(startSentence, endSentence).trim();
  }

  private int extractIssuesCount(@NonNull DataDocument dataDocument) {
    return dataDocument.getCountDocument().getIssues();
  }

  private int extractSentenceCount(@NonNull DataDocument dataDocument) {
    return dataDocument.getCountDocument().getSentences();
  }

  private int extractWordCount(@NonNull DataDocument dataDocument) {
    return dataDocument.getCountDocument().getWords();
  }

  private int searchEnd(@NonNull String detailText, @NonNull int originalEnd) {
    List<Integer> positions = new ArrayList<>();
    positions.add(detailText.indexOf(".", originalEnd));
    positions.add(detailText.indexOf("!", originalEnd));
    positions.add(detailText.indexOf("?", originalEnd));

    List<Integer> collect = positions.stream()
            .filter(integer -> integer != -1)
            .collect(Collectors.toList());
    int firstPosition = detailText.length();
    for (Integer integer : collect) {
      firstPosition = Math.min(firstPosition, integer);
    }
    if (firstPosition == detailText.length()) {
      return detailText.length();
    }
    //Include the found punctuation mark
    return firstPosition + 1;
  }

  private int searchStart(@NonNull String detailText, @NonNull int originalBegin) {
    String startsWith = detailText.substring(0, originalBegin);
    List<Integer> positions = new ArrayList<>();
    positions.add(startsWith.lastIndexOf("."));
    positions.add(startsWith.lastIndexOf("!"));
    positions.add(startsWith.lastIndexOf("?"));
    List<Integer> collect = positions.stream()
            .filter(integer -> integer != -1)
            .collect(Collectors.toList());

    int firstPosition = 0;

    for (Integer integer : collect) {
      firstPosition = Math.max(firstPosition, integer);
    }
    if (firstPosition == 0) {
      return 0;
    }
    //Do not include found punctuation mark
    return firstPosition + 1;
  }

  @NonNull
  private String extractScoreRatingColor(@NonNull DataDocument dataDocument) {
    return dataDocument.getQualityDocument().getStatus();
  }

  private int extractScore(@NonNull DataDocument dataDocument) {
    return dataDocument.getQualityDocument().getScore();
  }

  @NonNull
  private Map<String, List<TextIssue>> extractIssues(@NonNull String detailedText, @NonNull DataDocument dataDocument) {
    List<IssueDocument> issueDocuments = dataDocument.getIssueDocuments();
    Map<String, String> aspectIdToNameMap = extractAspectMap(dataDocument);
    Map<String, List<TextIssue>> textIssuesToAspect = new HashMap<>();
    for (IssueDocument issueDocument : issueDocuments) {
      TextIssue textIssues = toTextIssue(detailedText, issueDocument);
      String aspectName = aspectIdToNameMap.get(issueDocument.getAspectId());
      if(aspectName == null) {
        throw new UnsupportedOperationException("Invalid field name for issues '" + issueDocument.getAspectId() + "'");
      }

      if (!textIssuesToAspect.containsKey(aspectName)) {
        textIssuesToAspect.put(aspectName, new ArrayList<>());
      }
      textIssuesToAspect.get(aspectName).add(textIssues);
    }
    return textIssuesToAspect;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AcrolinxCheckQualityCacheKey cacheKey = (AcrolinxCheckQualityCacheKey) o;
    return Objects.equals(text, cacheKey.text) &&
            Objects.equals(checkOptionsDocument, cacheKey.checkOptionsDocument) &&
            Objects.equals(settings, cacheKey.settings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, checkOptionsDocument, settings);
  }
}
