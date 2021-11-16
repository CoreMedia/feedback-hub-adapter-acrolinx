import HBoxLayout from "@jangaroo/ext-ts/layout/container/HBox";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import AcrolinxSidebarPanelBase from "./AcrolinxSidebarPanelBase";

interface AcrolinxSidebarPanelConfig extends Config<AcrolinxSidebarPanelBase> {
}

class AcrolinxSidebarPanel extends AcrolinxSidebarPanelBase {
  declare Config: AcrolinxSidebarPanelConfig;

  static override readonly xtype: string = "com.coremedia.labs.plugins.feedbackhub.acrolinx.config.acrolinxSidebarPanel";

  constructor(config: Config<AcrolinxSidebarPanel> = null) {
    super(ConfigUtils.apply(Config(AcrolinxSidebarPanel, {
      padding: "0 0 0 0",
      minHeight: 300,

      items: [
      ],
      layout: Config(HBoxLayout, { align: "stretch" }),
    }), config));
  }
}

export default AcrolinxSidebarPanel;
