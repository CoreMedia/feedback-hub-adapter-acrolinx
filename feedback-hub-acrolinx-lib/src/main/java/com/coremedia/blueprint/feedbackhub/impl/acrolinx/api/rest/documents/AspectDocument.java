package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AspectDocument {

  @JsonProperty("goalId")
  private String aspectdId;
  @JsonProperty("displayName")
  private String aspectName;
  @JsonProperty("issues")
  private String issues;

  public String getAspectdId() {
    return aspectdId;
  }

  public void setAspectdId(String aspectdId) {
    this.aspectdId = aspectdId;
  }

  public String getAspectName() {
    return aspectName;
  }

  public void setAspectName(String aspectName) {
    this.aspectName = aspectName;
  }

  public String getIssues() {
    return issues;
  }

  public void setIssues(String issues) {
    this.issues = issues;
  }

  @Override
  public String toString() {
    return "AspectDocument{" +
           "goalId='" + aspectdId + '\'' +
           ", aspectName='" + aspectName + '\'' +
           ", issues='" + issues + '\'' +
           '}';
  }
}
