package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueDocument {

    @JsonProperty("aspectId")
    private String aspectId;
    @JsonProperty("internalName")
    private String issueName;
    @JsonProperty("guidanceHtml")
    private String issueGuidance;
    @JsonProperty("displayNameHtml")
    private String displayNameHtml;
    @JsonProperty("suggestions")
    private List<SuggestionDocument> issueSuggestions;
    @JsonProperty("positionalInformation")
    private PositionDocument positionDocument;
    @JsonProperty("displaySurface")
    private String displaySurface;

    public String getAspectId() {
        return aspectId;
    }

    public void setAspectId(String aspectId) {
        this.aspectId = aspectId;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getIssueGuidance() {
        return issueGuidance;
    }

    public void setIssueGuidance(String issueGuidance) {
        this.issueGuidance = issueGuidance;
    }

    public List<SuggestionDocument> getIssueSuggestions() {
        return issueSuggestions;
    }

    public void setIssueSuggestions(List<SuggestionDocument> issueSuggestions) {
        this.issueSuggestions = issueSuggestions;
    }

    public PositionDocument getPositionDocument() {
        return positionDocument;
    }

    public void setPositionDocument(PositionDocument positionDocument) {
        this.positionDocument = positionDocument;
    }

    public String getDisplayNameHtml() {
        return displayNameHtml;
    }

    public void setDisplayNameHtml(String displayNameHtml) {
        this.displayNameHtml = displayNameHtml;
    }

  public String getDisplaySurface() {
    return displaySurface;
  }

  public void setDisplaySurface(String displaySurface) {
    this.displaySurface = displaySurface;
  }

  @Override
    public String toString() {
        return "IssueDocument{" +
               "aspectId='" + aspectId + '\'' +
               ", issueName='" + issueName + '\'' +
               ", issueGuidance='" + issueGuidance + '\'' +
               ", issueSuggestions=" + issueSuggestions +
               ", positionDocument=" + positionDocument +
               '}';
    }
}
