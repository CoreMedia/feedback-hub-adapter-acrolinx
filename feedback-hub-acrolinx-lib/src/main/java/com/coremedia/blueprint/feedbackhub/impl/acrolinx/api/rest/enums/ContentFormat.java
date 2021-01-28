package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.enums;

public enum ContentFormat {

    TEXT("TEXT"),
    XML("XML"),
    HTML("HTML"),
    MARKDOWN("MARKDOWN"),
    AUTO("AUTO");

    private String contentType;

    ContentFormat(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
