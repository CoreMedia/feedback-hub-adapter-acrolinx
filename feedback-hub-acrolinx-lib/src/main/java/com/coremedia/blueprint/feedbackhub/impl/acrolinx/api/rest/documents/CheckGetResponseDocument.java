package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckGetResponseDocument {

  @JsonProperty("data")
  private DataDocument dataDocument;

  @JsonProperty("progress")
  private ProgressDocument progressDocument;

  public void setDataDocument(DataDocument dataDocument) {
    this.dataDocument = dataDocument;
  }

  public DataDocument getDataDocument() {
    return dataDocument;
  }

  public ProgressDocument getProgressDocument() {
    return progressDocument;
  }

  public void setProgressDocument(ProgressDocument progressDocument) {
    this.progressDocument = progressDocument;
  }

  @Override
  public String toString() {
    return "CheckGetResponseDocument{" +
           "dataDocument=" + dataDocument +
           ", progressDocument=" + progressDocument +
           '}';
  }
}
