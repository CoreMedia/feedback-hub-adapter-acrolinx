package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

public interface AcrolinxSettings {

  @Nullable
  String getClientLocale();

  @NonNull
  String getAccessToken();

  @NonNull
  String getSignature();

  @NonNull
  String getTenant();
}