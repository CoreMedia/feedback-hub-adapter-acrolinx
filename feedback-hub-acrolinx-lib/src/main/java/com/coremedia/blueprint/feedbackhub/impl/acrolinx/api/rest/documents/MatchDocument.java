package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDocument {

    @JsonProperty("extractedPart")
    private String extractedPart;
    @JsonProperty("extractedBegin")
    private int extractedBegin;
    @JsonProperty("extractedEnd")
    private int extractedEnd;

    @JsonProperty("originalPart")
    private String originalPart;
    @JsonProperty("originalBegin")
    private int originalBegin;
    @JsonProperty("originalEnd")
    private int originalEnd;

    public String getExtractedPart() {
        return extractedPart;
    }

    public void setExtractedPart(String extractedPart) {
        this.extractedPart = extractedPart;
    }

    public String getOriginalPart() {
        return originalPart;
    }

    public void setOriginalPart(String originalPart) {
        this.originalPart = originalPart;
    }

    public int getExtractedBegin() {
        return extractedBegin;
    }

    public void setExtractedBegin(int extractedBegin) {
        this.extractedBegin = extractedBegin;
    }

    public int getExtractedEnd() {
        return extractedEnd;
    }

    public void setExtractedEnd(int extractedEnd) {
        this.extractedEnd = extractedEnd;
    }

    public int getOriginalBegin() {
        return originalBegin;
    }

    public void setOriginalBegin(int originalBegin) {
        this.originalBegin = originalBegin;
    }

    public int getOriginalEnd() {
        return originalEnd;
    }

    public void setOriginalEnd(int originalEnd) {
        this.originalEnd = originalEnd;
    }

    @Override
    public String toString() {
        return "MatchDocument{" +
                "extractedPart='" + extractedPart + '\'' +
                ", originalPart='" + originalPart + '\'' +
                '}';
    }
}
