package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.cmsEntities.TextQuality;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface AcrolinxService {
  @Nullable
  TextQuality getCheckResults(@NonNull String text, @NonNull AcrolinxSettings acrolinxSettings);
}
