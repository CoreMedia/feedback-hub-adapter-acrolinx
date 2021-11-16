import LocaleUtil from "@coremedia/studio-client.cap-base-models/locale/LocaleUtil";
import session from "@coremedia/studio-client.cap-rest-client/common/session";
import Content from "@coremedia/studio-client.cap-rest-client/content/Content";
import ContentPropertyNames from "@coremedia/studio-client.cap-rest-client/content/ContentPropertyNames";
import StatefulTextField from "@coremedia/studio-client.ext.ui-components/components/StatefulTextField";
import CoreMediaRichTextArea from "@coremedia/studio-client.main.editor-components/sdk/components/CoreMediaRichTextArea";
import editorContext from "@coremedia/studio-client.main.editor-components/sdk/editorContext";
import IPropertyFieldRegistry from "@coremedia/studio-client.main.editor-components/sdk/premular/IPropertyFieldRegistry";
import PremularBase from "@coremedia/studio-client.main.editor-components/sdk/premular/PremularBase";
import TeaserOverlayPropertyField from "@coremedia/studio-client.main.editor-components/sdk/premular/fields/teaser/TeaserOverlayPropertyField";
import remoteControlHandlerRegistry from "@coremedia/studio-client.main.editor-components/sdk/remotecontrol/remoteControlHandlerRegistry";
import PropertyEditorUtil from "@coremedia/studio-client.main.editor-components/sdk/util/PropertyEditorUtil";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import Window from "@jangaroo/ext-ts/window/Window";
import { as, asConfig, bind, is } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import trace from "@jangaroo/runtime/trace";
import AcrolinxAdapterCustomizer from "../AcrolinxAdapterCustomizer";
import AcrolinxSidebarCustomizer from "./AcrolinxSidebarCustomizer";
import AcrolinxSidebarPanel from "./AcrolinxSidebarPanel";

interface AcrolinxSidebarPanelBaseConfig extends Config<FeedbackItemPanel> {
}

/**
 * Check https://acrolinx.github.io/acrolinx-sidebar-demo/samples/multi-editor.html to see some other
 * examples and API usage.
 *
 * Also: https://github.com/acrolinx/sidebar-sdk-js
 */
class AcrolinxSidebarPanelBase extends FeedbackItemPanel {
  declare Config: AcrolinxSidebarPanelBaseConfig;

  static readonly #ACROLINX_SIDEBAR_JS: string = "https://unpkg.com/@acrolinx/sidebar-sdk/dist/acrolinx-sidebar-sdk.js";

  #acrolinxPlugin: any;

  #multiAdapter: any;

  constructor(config: Config<AcrolinxSidebarPanel> = null) {
    super(config);
  }

  protected override afterRender(): void {
    super.afterRender();
    this.#loadSidebar();

    //use full height, we do this manually since usually the parent would only use the required height.
    const parent: Window = this.getFeedbackHubWindow();
    parent.on("resize", bind(this, this.#adaptHeight));
    this.#adaptHeight(parent);
  }

  /**
   * The listener function for adapting the height.
   * Since we customize the DOM manually with the Sidebar, we have to take care of that manually.
   * @param parent the Feedback Window
   */
  #adaptHeight(parent: Window): void {
    this.setHeight(asConfig(parent).height - 160);
    const target = this.#getTargetElement();
    if (target) {
      target.setAttribute("style", "height:" + (asConfig(parent).height - 166) + "px;width:" + (asConfig(parent).width - 24) + "px;background-color:#FFF;");
    }
  }

  /**
   * Ensures the loading of the Acrolinx Sidebar JavaScript.
   * The script is loaded from an external URL, so ensure that the Studio CSP
   * settings are set according to the documentation of this integration.
   */
  #loadSidebar(): void {
    if (!window["acrolinxSidebar"]) {
      const script: any = window.document.createElement("script");
      script.src = AcrolinxSidebarPanelBase.#ACROLINX_SIDEBAR_JS;
      script.addEventListener("load", (): void =>
        this.#initAcrolinx(),
      );
      window.document["head"].appendChild(script);
    } else {
      this.#initAcrolinx();
    }
  }

  /**
   * Executes everytime the Acrolinx feedback panel is rendered.
   * The external JS is loaded at this point.
   */
  #initAcrolinx(): void {
    const server = "https://" + this.feedbackItem["serverAddress"];
    const clientSignature: string = this.feedbackItem["clientSignature"];
    const profileId: string = this.feedbackItem["profileId"];
    let uiMode: string = null;

    //checker settings, we ignore options when there is a guidance profile mapping available
    let checkSettings: any = null;
    if (profileId) {
      trace("[INFO]", "Found Acrolinx guidance profile \"" + profileId + "\", ignoring options.");
      uiMode = "noOptions";
      checkSettings = { "profileId": profileId };
    }

    window["acrolinxSidebar"] = new window["acrolinx"].plugins.initFloatingSidebar({ asyncStorage: new window["acrolinx"].plugins.AsyncLocalStorage() });

    const placeholderId: string = this.#getTargetElement().id;
    this.#acrolinxPlugin = new window["acrolinx"].plugins.AcrolinxPlugin({
      serverAddress: server,
      sidebarContainerId: placeholderId,
      showServerSelector: false,
      clientLocale: LocaleUtil.getLocale(),
      clientSignature: clientSignature,
      uiMode: uiMode,
      checkSettings: checkSettings,
      getDocumentReference: (): string =>
        this.#getDocumentRef()
      ,
      clientComponents: [
        {
          id: "com.coremedia.labs.feedback.acrolinx",
          name: "CoreMedia Feedback Hub Adapter for Acrolinx",
          version: session._.getConnection().getLoginService().getVersion(),
          category: "MAIN",
        },
      ],
    });

    //register an editor adapter for every field that has been configured by the user for the given document type
    this.#registerEditorAdapters(this.#acrolinxPlugin);

    //finished with configuration, let's start the plugin
    this.#acrolinxPlugin.init();

    //once loaded, we try to fix the styling a little bit -> 300px width is not enough for us
    AcrolinxSidebarCustomizer.styleAcrolinx(this.#getTargetElement());

    //customize adapters that that changes are actually applied to the content
    AcrolinxAdapterCustomizer.init();

    //remove floating sidebar
    window["acrolinxSidebar"].remove();
  }

  /**
   * Resolves inner target element of this container.
   * This is where we attach the Acrolinx Sidebar.
   */
  #getTargetElement(): any {
    try {
      return this.el.dom["querySelector"]("[data-ref='targetEl']");
    } catch (e) {
      if (is(e, Error)) {
        return null;
      } else throw e;
    }
  }

  /**
   * Uses the field registry and content to search for matching editors inside the premular.
   * @param acrolinxPlugin the Acrolinx plugin to register adapters for.
   */
  #registerEditorAdapters(acrolinxPlugin: any): void {
    const propertyNames: Array<any> = this.feedbackItem["properties"];
    const content: Content = this.contentExpression.getValue();
    const registry = AcrolinxSidebarPanelBase.#getFieldRegistry();

    this.#multiAdapter = new window["acrolinx"].plugins.adapter.MultiEditorAdapter({
      documentHeader: "<!DOCTYPE xml>",
      rootElement: { tagName: "coremedia" },
    });

    for (const propertyName of propertyNames as string[]) {
      const descriptor = content.getType().getDescriptor(propertyName);
      if (descriptor) {
        const field: any = registry.getPropertyField(ContentPropertyNames.PROPERTIES + "." + propertyName, content.getType().getName());
        const fieldName = descriptor.name;
        const visibleName = PropertyEditorUtil.getLocalizedString(content.getType().getName(), fieldName, "text", fieldName);

        const attr: Record<string, any> = {
          attributes: {
            "class": fieldName,
            "data-visibleName": visibleName,
          },
        };

        if (field && field.xtype && field.rendered) {
          if (field.xtype === CoreMediaRichTextArea.xtype) {
            const ckEditorInstance: any = field.getCKEditor();
            const richtextId: string = ckEditorInstance.element.getId();
            this.#multiAdapter.addSingleAdapter(new window["acrolinx"].plugins.adapter.CKEditorAdapter({ editorId: richtextId }), attr);
          } else if (field.xtype == StatefulTextField.xtype) {
            const fieldId: string = field.getInputId();
            this.#multiAdapter.addSingleAdapter(new window["acrolinx"].plugins.adapter.InputAdapter({ editorId: fieldId }), attr);
          } else if (field.xtype == TeaserOverlayPropertyField.xtype) {
            const overlayId: string = field.getInputId();
            this.#multiAdapter.addSingleAdapter(new window["acrolinx"].plugins.adapter.InputAdapter({ editorId: overlayId }), attr);
          } else {
            trace("[INFO]", "Acrolinx integration found no suitable editor for property \"" + propertyName + "\"");
          }
        }
      }
    }

    // Register multiAdapter to the main Acrolinx plugin
    acrolinxPlugin.registerAdapter(this.#multiAdapter);
  }

  /**
   * Returns the editor registry that we use to resolve the element ids
   * for Acrolinx.
   */
  static #getFieldRegistry(): IPropertyFieldRegistry {
    const activePremular = as(editorContext._.getWorkArea().getActiveTab(), PremularBase);
    return activePremular["getDocumentFormPropertyFieldRegistry"]();
  }

  /**
   * Returns the unique reference for the Acrolinx system.
   * We use the remote control URL here.
   */
  #getDocumentRef(): string {
    const content: Content = this.contentExpression.getValue();
    return remoteControlHandlerRegistry.createRemoteControlUrl(content);
  }

  protected override onDestroy(): void {
    this.#multiAdapter.removeAllAdapters();
    super.onDestroy();
  }
}

export default AcrolinxSidebarPanelBase;
