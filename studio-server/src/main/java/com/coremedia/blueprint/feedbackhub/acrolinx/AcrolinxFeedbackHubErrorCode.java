package com.coremedia.blueprint.feedbackhub.acrolinx;

import com.coremedia.feedbackhub.adapter.FeedbackHubErrorCode;

/**
 * Error codes for Acrolinx adapter
 */
enum AcrolinxFeedbackHubErrorCode implements FeedbackHubErrorCode {
  ACCESS_TOKEN_NOT_SET,
  SIGNATURE_NOT_SET,
  TENANT_NOT_SET
}
