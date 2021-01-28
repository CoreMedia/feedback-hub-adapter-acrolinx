package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties (ignoreUnknown = true)
public class CheckPostResponseDocument {

    @JsonProperty("data")
    private CheckDataDocument checkDataDocument;

    public CheckDataDocument getCheckDataDocument() {
        return checkDataDocument;
    }

    public void setCheckDataDocument(CheckDataDocument checkDataDocument) {
        this.checkDataDocument = checkDataDocument;
    }

    @Override
    public String toString() {
        return "CheckPostResponseDocument{" +
                "checkDataDocument=" + checkDataDocument +
                '}';
    }
}
