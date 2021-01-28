package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckDataDocument {

    @JsonProperty("id")
    private String checkId;

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    @Override
    public String toString() {
        return "CheckDataDocument{" +
                "checkId='" + checkId + '\'' +
                '}';
    }
}
