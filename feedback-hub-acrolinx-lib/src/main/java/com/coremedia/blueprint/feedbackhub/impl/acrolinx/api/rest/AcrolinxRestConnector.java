package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckBodyDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckGetResponseDocument;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface AcrolinxRestConnector {

  @Nullable
  CheckGetResponseDocument checkText(@NonNull CheckBodyDocument bodyDocument,
                                     @NonNull AcrolinxSettings acrolinxSettings) throws AcrolinxException;
}
