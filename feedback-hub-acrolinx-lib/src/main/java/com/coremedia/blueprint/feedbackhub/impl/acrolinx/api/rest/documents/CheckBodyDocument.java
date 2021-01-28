package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckBodyDocument {

    @JsonProperty("content")
    private String content;
    @JsonProperty("checkOptions")
    private CheckOptionsDocument checkOptionsDocument;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CheckOptionsDocument getCheckOptionsDocument() {
        return checkOptionsDocument;
    }

    public void setCheckOptionsDocument(CheckOptionsDocument checkOptionsDocument) {
        this.checkOptionsDocument = checkOptionsDocument;
    }
}
