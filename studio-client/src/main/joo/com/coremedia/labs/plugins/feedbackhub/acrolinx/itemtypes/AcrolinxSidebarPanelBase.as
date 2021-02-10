package com.coremedia.labs.plugins.feedbackhub.acrolinx.itemtypes {
import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentPropertyNames;
import com.coremedia.cms.editor.sdk.components.CoreMediaRichTextArea;
import com.coremedia.cms.editor.sdk.editorContext;
import com.coremedia.cms.editor.sdk.premular.IPropertyFieldRegistry;
import com.coremedia.cms.editor.sdk.premular.PremularBase;
import com.coremedia.cms.editor.sdk.premular.fields.teaser.TeaserOverlayPropertyField;
import com.coremedia.cms.editor.sdk.remotecontrol.remoteControlHandlerRegistry;
import com.coremedia.cms.studio.base.cap.models.locale.LocaleUtil;
import com.coremedia.cms.studio.feedbackhub.components.itempanels.FeedbackItemPanel;
import com.coremedia.ui.components.StatefulTextField;

import ext.window.Window;

[ResourceBundle('com.coremedia.labs.plugins.feedbackhub.acrolinx.FeedbackHubAcrolinx')]
public class AcrolinxSidebarPanelBase extends FeedbackItemPanel {

  public function AcrolinxSidebarPanelBase(config:AcrolinxSidebarPanel = null) {
    super(config);
  }

  override protected function afterRender():void {
    super.afterRender();
    loadSidebar();

    //use full height, we do this manually since usually the parent would only use the required height.
    var parent:Window = getFeedbackHubWindow();
    parent.on('resize', adaptHeight);
    adaptHeight(parent);
  }

  private function adaptHeight(parent:Window):void {
    this.setHeight(parent.height - 160);
    var target:* = getTargetElement();
    if (target) {
      target.setAttribute("style", "height:" + (parent.height - 166) + "px;width:" + (parent.width - 24) + "px;");
    }
  }

  private function loadSidebar():void {
    if (!window.acrolinxSidebar) {
      var script:* = window.document.createElement('script');
      script.src = "https://unpkg.com/@acrolinx/sidebar-sdk/dist/acrolinx-sidebar-sdk.js";
      script.addEventListener('load', function ():void {
        initAcrolinx();
      });
      window.document['head'].appendChild(script);
    }
    else {
      initAcrolinx();
    }
  }

  private function initAcrolinx():void {
    var server:String = 'https://' + feedbackItem['serverAddress'];
    var clientSignature:String = feedbackItem['clientSignature'];
    var profileId:String = feedbackItem['profileId'];
    var uiMode:String = null;
    var checkSettings:Object = null;
    if (profileId) {
      trace('[INFO]', 'Found Acrolinx guidance profile "' + profileId + '", ignoring options.');
      uiMode = 'noOptions';
      checkSettings = {
        'profileId': profileId
      }
    }

    window.acrolinxSidebar = new window.acrolinx.plugins.initFloatingSidebar({asyncStorage: new window.acrolinx.plugins.AsyncLocalStorage()});

    var placeholderId:String = getTargetElement().id;
    var acrolinxPlugin:* = new window.acrolinx.plugins.AcrolinxPlugin({
      serverAddress: server,
      sidebarContainerId: placeholderId,
      showServerSelector: false,
      clientLocale: LocaleUtil.getLocale(),
      clientSignature: clientSignature,
      uiMode: uiMode,
      checkSettings: checkSettings,
      getDocumentReference: function ():String {
        return getDocumentReference();
      }
    });

    //register an editor adapter for every field that has been configured by the user for the given document type
    registerEditorAdapters(acrolinxPlugin);

    acrolinxPlugin.init();

    AcrolinxSidebarCustomizer.styleAcrolinx(getTargetElement());
    window.acrolinxSidebar.remove();
  }

  /**
   * Resolves inner target element of this container.
   * This is where we attache the Acrolinx Sidebar.
   */
  private function getTargetElement():* {
    try {
      return this.el.dom['querySelector']("[data-ref='targetEl']");
    } catch (e:Error) {
      return null;
    }
  }

  /**
   * Uses the field registry and content to search for matching editors inside the premular.
   * @param acrolinxPlugin the Acrolinx plugin to register adapters for.
   */
  private function registerEditorAdapters(acrolinxPlugin:*):void {
    var propertyNames:Array = feedbackItem['properties'];
    var content:Content = contentExpression.getValue();
    var registry:IPropertyFieldRegistry = getFieldRegistry();


    var multiAdapter = new window.acrolinx.plugins.adapter.MultiEditorAdapter({
      // Optional: Can be used to set the DOCTYPE
      // documentHeader: '<!DOCTYPE html>\n',
      // Optional: Wrapper around the complete concatenated html
      // rootElement: {tagName: 'html'}
      // beforeCheck: function (multiAdapterArgument) {
      //   multiAdapterArgument.removeAllAdapters();
      //   multiAdapterArgument.addSingleAdapter(...)
      // }
    });

    for each(var propertyName:String in propertyNames) {
      var descriptor:CapPropertyDescriptor = content.getType().getDescriptor(propertyName);
      if (descriptor) {
        var field:* = registry.getPropertyField(ContentPropertyNames.PROPERTIES + '.' + propertyName, content.getType().getName());

        if (field && field.xtype) {
          if (field.xtype === CoreMediaRichTextArea.xtype) {
            var richtextId:String = field.getCKEditor().element.getId();
            multiAdapter.addSingleAdapter(new window.acrolinx.plugins.adapter.CKEditorAdapter({editorId: richtextId}));
          }
          else if (field.xtype == StatefulTextField.xtype) {
            var fieldId:String = field.getInputId();
            multiAdapter.addSingleAdapter(new window.acrolinx.plugins.adapter.InputAdapter({editorId: fieldId}));
          }
          else if (field.xtype == TeaserOverlayPropertyField.xtype) {
            var overlayId:String = field.getInputId();
            multiAdapter.addSingleAdapter(new window.acrolinx.plugins.adapter.InputAdapter({editorId: overlayId}));
          }
          else {
            trace('[INFO]', 'Acrolinx integration found no suitable editor for property "' + propertyName + '"');
          }
        }
      }
    }

    // Register multiAdapter to the main Acrolinx plugin
    acrolinxPlugin.registerAdapter(multiAdapter);
  }

  /**
   * Returns the editor registry that we use to resolve the element ids
   * for Acrolinx.
   * @return
   */
  private static function getFieldRegistry():IPropertyFieldRegistry {
    var activePremular:PremularBase = editorContext.getWorkArea().getActiveTab() as PremularBase;
    return activePremular['getDocumentFormPropertyFieldRegistry']();
  }

  /**
   * Returns the unique reference for the Acrolinx system.
   * We use the remote control URL here.
   */
  private function getDocumentReference():String {
    var content:Content = contentExpression.getValue();
    return remoteControlHandlerRegistry.createRemoteControlUrl(content);
  }
}
}
