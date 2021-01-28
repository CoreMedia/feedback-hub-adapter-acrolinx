package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest;

public class AcrolinxException extends RuntimeException {
  public AcrolinxException(String message) {
    super(message);
  }

  public AcrolinxException(Throwable cause) {
    super(cause);
  }

  public AcrolinxException(String message, Throwable cause) {
    super(message, cause);
  }
}
