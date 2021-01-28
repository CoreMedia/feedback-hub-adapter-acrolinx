package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.enums.ContentFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckOptionsDocument {

    private ContentFormat contentFormat;
    private String audienceId;
    private List<String> analysisTypes;
    private List<String> reportTypes;

    public CheckOptionsDocument() {
    }

    public CheckOptionsDocument(ContentFormat contentFormat, String audienceId, List<String> analysisTypes, List<String> reportTypes) {
        this.contentFormat = contentFormat;
        this.audienceId = audienceId;
        this.analysisTypes = analysisTypes;
        this.reportTypes = reportTypes;
    }

    public ContentFormat getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(ContentFormat contentFormat) {
        this.contentFormat = contentFormat;
    }

    public String getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(String audienceId) {
        this.audienceId = audienceId;
    }

    public List<String> getAnalysisTypes() {
        return analysisTypes;
    }

    public void setAnalysisTypes(List<String> analysisTypes) {
        this.analysisTypes = analysisTypes;
    }

    public List<String> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(List<String> reportTypes) {
        this.reportTypes = reportTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CheckOptionsDocument that = (CheckOptionsDocument) o;
        return contentFormat == that.contentFormat &&
               Objects.equals(audienceId, that.audienceId) &&
               Objects.equals(analysisTypes, that.analysisTypes) &&
               Objects.equals(reportTypes, that.reportTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentFormat, audienceId, analysisTypes, reportTypes);
    }
}
