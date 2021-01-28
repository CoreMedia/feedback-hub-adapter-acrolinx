package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDocument {

    @JsonProperty("displayName")
    private String reportName;
    @JsonProperty("link")
    private String link;
    @JsonProperty("linkAuthenticated")
    private String linkAuthenticated;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkAuthenticated() {
        return linkAuthenticated;
    }

    public void setLinkAuthenticated(String linkAuthenticated) {
        this.linkAuthenticated = linkAuthenticated;
    }

    @Override
    public String toString() {
        return "ReportDocument{" +
                "reportName='" + reportName + '\'' +
                ", link='" + link + '\'' +
                ", linkAuthenticated='" + linkAuthenticated + '\'' +
                '}';
    }
}
