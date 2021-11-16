import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import editorContext from "@coremedia/studio-client.main.editor-components/sdk/editorContext";
import Premular from "@coremedia/studio-client.main.editor-components/sdk/premular/Premular";
import Ext from "@jangaroo/ext-ts";
import { as } from "@jangaroo/runtime";
import { AnyFunction } from "@jangaroo/runtime/types";

class AcrolinxAdapterCustomizer {
  static #initialized: boolean = false;

  constructor() {
  }

  static init(): void {
    if (!AcrolinxAdapterCustomizer.#initialized) {
      AcrolinxAdapterCustomizer.#initialized = true;
      AcrolinxAdapterCustomizer.#customizeAcrolinxAdapters();
    }
  }

  /**
   * Customizes the original Acrolinx adapters for CoreMedia property editor usage
   */
  static #customizeAcrolinxAdapters(): void {
    AcrolinxAdapterCustomizer.#overrideInputAdapter();
    AcrolinxAdapterCustomizer.#overrideCKEditorAdapter();
  }

  static #overrideInputAdapter(): void {
    const originalCkReplacement: AnyFunction = window["acrolinx"].plugins.adapter.InputAdapter.prototype["replaceRanges"];
    window["acrolinx"].plugins.adapter.InputAdapter.prototype["replaceRanges"] = function(checkId: string, matchesWithReplacementArg: Array<any>): void {
      const that = this;
      originalCkReplacement.call(this, checkId, matchesWithReplacementArg);
      const elementId: string = that.element.id;
      const value: string = that.element.value;
      const componentId = elementId.substring(0, elementId.lastIndexOf("-"));
      const field: any = Ext.getCmp(componentId);

      const premular = as(editorContext._.getWorkArea().getActiveTab(), Premular);
      const propertyPath: string = field.initialConfig.name;
      const ve = ValueExpressionFactory.create(propertyPath, premular.getContent());
      ve.setValue(value);
    };
  }

  static #overrideCKEditorAdapter(): void {
    const originalReplacement: AnyFunction = window["acrolinx"].plugins.adapter.CKEditorAdapter.prototype["replaceRanges"];
    window["acrolinx"].plugins.adapter.CKEditorAdapter.prototype["replaceRanges"] = function(checkId: string, matchesWithReplacementArg: Array<any>): void {
      const that = this;
      originalReplacement.call(this, checkId, matchesWithReplacementArg);
      that.getEditor().fire("change");
    };
  }
}

export default AcrolinxAdapterCustomizer;
