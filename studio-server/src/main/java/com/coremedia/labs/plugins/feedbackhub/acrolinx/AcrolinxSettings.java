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

  /**
   * Returns a comma separated list of property names that should be analyzed via Acrolinx.
   */
  String getPropertyNames();

}
