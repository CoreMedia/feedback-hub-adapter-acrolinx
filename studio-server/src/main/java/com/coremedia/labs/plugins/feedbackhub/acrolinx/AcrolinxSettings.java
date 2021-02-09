package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 *
 */
public interface AcrolinxSettings {

  /**
   * Returns the Acrolinx server to work on.
   */
  @NonNull
  String getServerAddress();

  /**
   * Returns the client signature used for authentication.
   */
  @NonNull
  String getClientSignature();

  /**
   * Returns a comma separated list of property names that should be analyzed via Acrolinx.
   */
  @NonNull
  String getPropertyNames();

  /**
   * Returns the access token for the Acrolinx SDK.
   * If not defined, the user has to select profiles by themself.
   */
  @Nullable
  String getAccessToken();

  /**
   * Enables an automatic mapping from CoreMedia content to a Acrolinx guidance profile.
   */
  @Nullable
  Object getContent();
}
