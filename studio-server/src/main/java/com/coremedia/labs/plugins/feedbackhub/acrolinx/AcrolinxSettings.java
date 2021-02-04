package com.coremedia.labs.plugins.feedbackhub.acrolinx;

/**
 *
 */
public interface AcrolinxSettings {

  /**
   * Returns the Acrolinx server to work on.
   */
  String getServerAddress();

  /**
   * Returns the client signature used for authentication.
   */
  String getClientSignature();

}
