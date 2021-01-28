package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountDocument {

    @JsonProperty("sentences")
    private int sentences;
    @JsonProperty("words")
    private int words;
    @JsonProperty("issues")
    private int issues;

    public int getSentences() {
        return sentences;
    }

    public void setSentences(int sentences) {
        this.sentences = sentences;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public int getIssues() {
        return issues;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "CountDocument{" +
                "sentences='" + sentences + '\'' +
                ", words='" + words + '\'' +
                ", issues='" + issues + '\'' +
                '}';
    }
}
