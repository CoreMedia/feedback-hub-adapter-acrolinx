package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportsDocument {

    @JsonProperty("scorecard")
    private ReportDocument scorecardReport;

    public ReportDocument getScorecardReport() {
        return scorecardReport;
    }

    public void setScorecardReport(ReportDocument scorecardReport) {
        this.scorecardReport = scorecardReport;
    }

    @Override
    public String toString() {
        return "ReportsDocument{" +
                "jsonReport=" + scorecardReport +
                '}';
    }
}
