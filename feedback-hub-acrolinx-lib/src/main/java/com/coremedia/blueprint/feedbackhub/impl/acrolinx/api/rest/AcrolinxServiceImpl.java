package com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.cmsEntities.TextQuality;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckOptionsDocument;
import com.coremedia.cache.Cache;
import com.coremedia.cache.EvaluationException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


public class AcrolinxServiceImpl implements AcrolinxService {
  private final Cache cache;
  private final AcrolinxRestConnector acrolinxRestConnector;
  private final CheckOptionsDocument checkOptionsDocument;

  public AcrolinxServiceImpl(@NonNull Cache cache,
                      @NonNull AcrolinxRestConnector acrolinxRestConnector,
                      @NonNull CheckOptionsDocument checkOptionsDocument) {
    this.cache = cache;
    this.acrolinxRestConnector = acrolinxRestConnector;
    this.checkOptionsDocument = checkOptionsDocument;
  }

  @Override
  @Nullable
  public TextQuality getCheckResults(@NonNull String text, @NonNull AcrolinxSettings acrolinxSettings) {
    AcrolinxCheckQualityCacheKey cacheKey = new AcrolinxCheckQualityCacheKey(acrolinxRestConnector, text, checkOptionsDocument, acrolinxSettings);
    try {
      return cache.get(cacheKey);
    } catch(EvaluationException e) {
      throw new AcrolinxException(e);
    }
  }
}
