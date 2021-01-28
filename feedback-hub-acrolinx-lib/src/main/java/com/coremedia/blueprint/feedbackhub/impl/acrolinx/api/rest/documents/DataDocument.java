package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDocument {

  @JsonProperty("id")
  private String id;
  @JsonProperty("quality")
  private QualityDocument qualityDocument;
  @JsonProperty("counts")
  private CountDocument countDocument;
  @JsonProperty(value = "goals", required = true)
  private List<AspectDocument> aspectsDocuments;
  @JsonProperty("issues")
  private List<IssueDocument> issueDocuments;
  @JsonProperty("reports")
  private ReportsDocument reportsDocument;

  public void setId(String id) {
    this.id = id;
  }

  public void setQualityDocument(QualityDocument qualityDocument) {
    this.qualityDocument = qualityDocument;
  }

  public void setCountDocuments(CountDocument countDocuments) {
    this.countDocument = countDocuments;
  }

  public void setAspectsDocuments(List<AspectDocument> aspectsDocuments) {
    this.aspectsDocuments = aspectsDocuments;
  }

  public void setIssueDocuments(List<IssueDocument> issueDocuments) {
    this.issueDocuments = issueDocuments;
  }

  public void setReportsDocument(ReportsDocument reportsDocument) {
    this.reportsDocument = reportsDocument;
  }

  public String getId() {
    return id;
  }

  public QualityDocument getQualityDocument() {
    return qualityDocument;
  }

  public CountDocument getCountDocument() {
    return countDocument;
  }

  @NonNull
  public List<AspectDocument> getAspectsDocuments() {
    return aspectsDocuments;
  }

  public List<IssueDocument> getIssueDocuments() {
    return issueDocuments;
  }

  public ReportsDocument getReportsDocument() {
    return reportsDocument;
  }

  @Override
  public String toString() {
    return "DataDocument{" +
           "id='" + id + '\'' +
           ", qualityDocument=" + qualityDocument +
           ", countDocuments=" + countDocument +
           ", aspectsDocuments=" + aspectsDocuments +
           ", issueDocuments=" + issueDocuments +
           ", reportsDocument=" + reportsDocument +
           '}';
  }
}

