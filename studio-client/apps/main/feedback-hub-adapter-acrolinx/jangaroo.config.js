const { jangarooConfig } = require("@jangaroo/core");

module.exports = {
  type: "code",
  sencha: {
    name: "com.coremedia.labs.plugins__studio-client.feedback-hub-adapter-acrolinx",
    namespace: "com.coremedia.labs.plugins.feedbackhub.acrolinx",
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.feedbackhub.acrolinx.AcrolinxFeedbackHubStudioPlugin",
        name: "FeedbackHub for Acrolinx",
      },
    ],
  },
};
