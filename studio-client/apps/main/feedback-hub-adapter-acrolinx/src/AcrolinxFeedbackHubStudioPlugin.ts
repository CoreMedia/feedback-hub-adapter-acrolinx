import CopyResourceBundleProperties from "@coremedia/studio-client.main.editor-components/configuration/CopyResourceBundleProperties";
import FeedbackHub_properties from "@coremedia/studio-client.main.feedback-hub-editor-components/FeedbackHub_properties";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
import AcrolinxFeedbackHubStudioPluginBase from "./AcrolinxFeedbackHubStudioPluginBase";
import FeedbackHubAcrolinx_properties from "./FeedbackHubAcrolinx_properties";

interface AcrolinxFeedbackHubStudioPluginConfig extends Config<AcrolinxFeedbackHubStudioPluginBase> {
}

class AcrolinxFeedbackHubStudioPlugin extends AcrolinxFeedbackHubStudioPluginBase {
  declare Config: AcrolinxFeedbackHubStudioPluginConfig;

  static readonly xtype: string = "com.coremedia.labs.plugins.feedbackhub.acrolinx.config.acrolinxFeedbackHubStudioPlugin";

  constructor(config: Config<AcrolinxFeedbackHubStudioPlugin> = null) {
    super(ConfigUtils.apply(Config(AcrolinxFeedbackHubStudioPlugin, {

      configuration: [
        new CopyResourceBundleProperties({
          destination: resourceManager.getResourceBundle(null, FeedbackHub_properties),
          source: resourceManager.getResourceBundle(null, FeedbackHubAcrolinx_properties),
        }),
      ],

    }), config));
  }
}

export default AcrolinxFeedbackHubStudioPlugin;
