package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDocument {

    @JsonProperty("matches")
    private List<MatchDocument> matches;

    public List<MatchDocument> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchDocument> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "PositionDocument{" +
                "matches=" + matches +
                '}';
    }
}
