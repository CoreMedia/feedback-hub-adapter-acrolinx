package com.coremedia.labs.plugins.feedbackhub.acrolinx;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * The settings of the Acrolinx adapter.
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
   * The actual backing struct which stores this settings.
   * We have to access it in order to read the profile mapping.
   */
  @Nullable
  Object getContent();
}
