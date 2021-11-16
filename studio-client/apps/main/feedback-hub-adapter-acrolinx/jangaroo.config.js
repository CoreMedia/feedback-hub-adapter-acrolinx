/** @type { import('@jangaroo/core').IJangarooConfig } */
module.exports = {
  type: "code",
  extName: "com.coremedia.labs.plugins__studio-client.feedback-hub-adapter-acrolinx",
  extNamespace: "com.coremedia.labs.plugins.feedbackhub.acrolinx",
  sencha: {
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.feedbackhub.acrolinx.AcrolinxFeedbackHubStudioPlugin",
        name: "FeedbackHub for Acrolinx",
      },
    ],
  },
  command: {
    build: {
      ignoreTypeErrors: true
    },
  },
};
