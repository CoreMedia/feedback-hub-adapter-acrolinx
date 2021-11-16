import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";

/**
 * Interface values for ResourceBundle "FeedbackHubAcrolinx".
 * @see FeedbackHubAcrolinx_properties#INSTANCE
 */
interface FeedbackHubAcrolinx_properties {

  acrolinx_iconCls: string;
  acrolinx_title: string;
  acrolinx_tooltip: string;
  acrolinx_ariaLabel: string;
  acrolinx_guidance_profile: string;
/**
 * Acrolinx Error Messages
 */
  acrolinx_error_SERVER_ADDRESS_NOT_SET: string;
  acrolinx_error_CLIENT_SIGNATURE_NOT_SET: string;
}

/**
 * Singleton for the current user Locale's instance of ResourceBundle "FeedbackHubAcrolinx".
 * @see FeedbackHubAcrolinx_properties
 */
const FeedbackHubAcrolinx_properties: FeedbackHubAcrolinx_properties = {
  acrolinx_iconCls: CoreIcons_properties.acrolinx,
  acrolinx_title: "Acrolinx",
  acrolinx_tooltip: "Check your content with Acrolinx",
  acrolinx_ariaLabel: "Acrolinx",
  acrolinx_guidance_profile: "<b>Guidance Profile:<\/b> {0}",
  acrolinx_error_SERVER_ADDRESS_NOT_SET: "Please provide a valid serverAddress in the configuration for {0}.",
  acrolinx_error_CLIENT_SIGNATURE_NOT_SET: "Please provide a valid clientSignature in the configuration for {0}.",
};

export default FeedbackHubAcrolinx_properties;
