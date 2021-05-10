package com.coremedia.labs.plugins.feedbackhub.acrolinx {
import com.coremedia.cms.editor.configuration.StudioPlugin;
import com.coremedia.cms.editor.sdk.IEditorContext;
import com.coremedia.cms.studio.feedbackhub.feedbackService;
import com.coremedia.labs.plugins.feedbackhub.acrolinx.itemtypes.AcrolinxSidebarPanel;

public class AcrolinxFeedbackHubStudioPluginBase extends StudioPlugin {
  public function AcrolinxFeedbackHubStudioPluginBase(config:AcrolinxFeedbackHubStudioPlugin = null) {
    super(config);
  }

  override public function init(editorContext:IEditorContext):void {
    super.init(editorContext);
    feedbackService.registerFeedbackItemPanel("acrolinxSidebar", AcrolinxSidebarPanel({}));
  }
}
}
