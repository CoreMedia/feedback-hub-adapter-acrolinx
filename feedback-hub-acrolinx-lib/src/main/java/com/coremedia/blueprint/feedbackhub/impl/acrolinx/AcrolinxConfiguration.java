package com.coremedia.blueprint.feedbackhub.impl.acrolinx;

import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxRestConnector;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxRestConnectorImpl;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxService;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.AcrolinxServiceImpl;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.documents.CheckOptionsDocument;
import com.coremedia.blueprint.feedbackhub.impl.acrolinx.api.rest.enums.ContentFormat;
import com.coremedia.cache.Cache;
import com.coremedia.springframework.xml.ResourceAwareXmlBeanDefinitionReader;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;


@Configuration
@ImportResource(reader = ResourceAwareXmlBeanDefinitionReader.class,
        value = {
                "classpath:/com/coremedia/cache/cache-services.xml"
        }
)
public class AcrolinxConfiguration {

  @Bean
  public AcrolinxRestConnector acrolinxRestConnector() {
    return new AcrolinxRestConnectorImpl(new RestTemplate());
  }

  @Bean
  public AcrolinxService acrolinxService(@NonNull Cache cache,
                                         @NonNull AcrolinxRestConnector acrolinxRestConnector,
                                         @NonNull CheckOptionsDocument checkOptionsDocument) {
    return new AcrolinxServiceImpl(cache, acrolinxRestConnector, checkOptionsDocument);
  }

  @Bean
  public CheckOptionsDocument checkOptionsDocument(@Value("${acrolinx.api.audienceId}") String audienceId) {
    return new CheckOptionsDocument(ContentFormat.TEXT,
            audienceId,
            ImmutableList.of("market_muse_analysis"),
            ImmutableList.of("scorecard", "extractedText"));
  }
}
