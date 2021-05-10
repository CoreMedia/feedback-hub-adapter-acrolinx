package com.coremedia.labs.plugins.feedbackhub.acrolinx {
import com.coremedia.cap.content.ContentPropertyNames;
import com.coremedia.cms.editor.sdk.editorContext;
import com.coremedia.cms.editor.sdk.premular.Premular;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpression;
import com.coremedia.ui.data.ValueExpressionFactory;

import ext.Ext;

public class AcrolinxAdapterCustomizer {
  private static var initialized:Boolean = false;

  public function AcrolinxAdapterCustomizer() {
  }

  public static function init():void {
    if(!initialized) {
      initialized = true;
      customizeAcrolinxAdapters();
    }
  }

  /**
   * Customizes the original Acrolinx adapters for CoreMedia property editor usage
   */
  private static function customizeAcrolinxAdapters():void {
    overrideInputAdapter();
    overrideCKEditorAdapter();
  }

  private static function overrideInputAdapter():void {
    var originalCkReplacement:Function = window.acrolinx.plugins.adapter.InputAdapter.prototype['replaceRanges'];
    window.acrolinx.plugins.adapter.InputAdapter.prototype['replaceRanges'] = function (checkId:String, matchesWithReplacementArg:Array):void {
      var that:* = this;
      originalCkReplacement.call(this, checkId, matchesWithReplacementArg);
      var elementId:String = that.element.id;
      var value:String = that.element.value;
      var componentId:String = elementId.substring(0, elementId.lastIndexOf('-'));
      var field:* = Ext.getCmp(componentId);

      var premular:Premular = editorContext.getWorkArea().getActiveTab() as Premular;
      var propertyPath:String = field.initialConfig.name;
      var ve:ValueExpression = ValueExpressionFactory.create(propertyPath, premular.getContent());
      ve.setValue(value);
    };
  }

  private static function overrideCKEditorAdapter():void {
    var originalReplacement:Function = window.acrolinx.plugins.adapter.CKEditorAdapter.prototype['replaceRanges'];
    window.acrolinx.plugins.adapter.CKEditorAdapter.prototype['replaceRanges'] = function (checkId:String, matchesWithReplacementArg:Array):void {
      var that:* = this;
      originalReplacement.call(this, checkId, matchesWithReplacementArg);
      that.getEditor().fire('change');
    };
  }
}
}
