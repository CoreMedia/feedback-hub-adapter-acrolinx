import StudioPlugin from "@coremedia/studio-client.main.editor-components/configuration/StudioPlugin";
import IEditorContext from "@coremedia/studio-client.main.editor-components/sdk/IEditorContext";
import feedbackService from "@coremedia/studio-client.main.feedback-hub-editor-components/feedbackService";
import Config from "@jangaroo/runtime/Config";
import AcrolinxFeedbackHubStudioPlugin from "./AcrolinxFeedbackHubStudioPlugin";
import AcrolinxSidebarPanel from "./itemtypes/AcrolinxSidebarPanel";

interface AcrolinxFeedbackHubStudioPluginBaseConfig extends Config<StudioPlugin> {
}

class AcrolinxFeedbackHubStudioPluginBase extends StudioPlugin {
  declare Config: AcrolinxFeedbackHubStudioPluginBaseConfig;

  constructor(config: Config<AcrolinxFeedbackHubStudioPlugin> = null) {
    super(config);
  }

  override init(editorContext: IEditorContext): void {
    super.init(editorContext);
    feedbackService._.registerFeedbackItemPanel("acrolinxSidebar", Config(AcrolinxSidebarPanel));
  }
}

export default AcrolinxFeedbackHubStudioPluginBase;
