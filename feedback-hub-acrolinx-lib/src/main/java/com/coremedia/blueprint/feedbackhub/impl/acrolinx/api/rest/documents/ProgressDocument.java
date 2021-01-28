package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressDocument {

  @JsonProperty("message")
  private String message;

  @JsonProperty("percent")
  private int percent;

  @JsonProperty("retryAfter")
  private int retryAfterSeconds;

  public int getRetryAfterSeconds() {
    return retryAfterSeconds;
  }

  public void setRetryAfterSeconds(int retryAfterSeconds) {
    this.retryAfterSeconds = retryAfterSeconds;
  }

  @Override
  public String toString() {
    return "ProgressDocument{" +
           "message='" + message + '\'' +
           ", percent=" + percent +
           ", retryAfterSeconds=" + retryAfterSeconds +
           '}';
  }
}
