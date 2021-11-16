import ResourceBundleUtil from "@jangaroo/runtime/l10n/ResourceBundleUtil";
import FeedbackHubAcrolinx_properties from "./FeedbackHubAcrolinx_properties";

/**
 * Overrides of ResourceBundle "FeedbackHubAcrolinx" for Locale "de".
 * @see FeedbackHubAcrolinx_properties#INSTANCE
 */
ResourceBundleUtil.override(FeedbackHubAcrolinx_properties, {
  acrolinx_title: "Acrolinx",
  acrolinx_tooltip: "Inhalte mit Acrolinx überprüfen",
  acrolinx_ariaLabel: "Acrolinx",
  acrolinx_guidance_profile: "<b>Prüfprofil:<\/b> {0}",
  acrolinx_error_SERVER_ADDRESS_NOT_SET: "Please provide a valid serverAddress in the configuration for {0}.",
  acrolinx_error_CLIENT_SIGNATURE_NOT_SET: "Please provide a valid clientSignature in the configuration for {0}.",
});
