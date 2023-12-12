
class AcrolinxSidebarCustomizer {
  static readonly #DELAY: number = 200;

  static styleAcrolinx(targetElement: any): void {
    //mmh, some custom styling for the iframe
    const iframe: any = targetElement.getElementsByTagName("iframe")[0];
    if (iframe) {
      iframe.style = "height:100%;height:100%;width:100%;border:1px solid #dadada;";
      AcrolinxSidebarCustomizer.#styleAcrolinxIFrame(iframe, 10000);
    }
  }

  static #styleAcrolinxIFrame(iframe: any, timeout: number): void {
    if (timeout < 0) {
      console.log("[WARN]", "Failed to apply custom styling for Acrolinx sidebar.");
      return;
    }

    timeout = timeout - AcrolinxSidebarCustomizer.#DELAY;
    window.setTimeout((): void => {
      if (!iframe.contentDocument) {
        AcrolinxSidebarCustomizer.#styleAcrolinxIFrame(iframe, timeout);
        return;
      }

      const sidebarContainer: any = iframe.contentDocument.getElementById("sidebarContainer");
      if (!sidebarContainer) {
        AcrolinxSidebarCustomizer.#styleAcrolinxIFrame(iframe, timeout);
        return;
      }

      const iframe2: any = sidebarContainer.firstChild;
      if (iframe2 && iframe2.contentDocument) {
        const appDiv: any = iframe2.contentDocument.getElementById("app");
        if (!appDiv) {
          AcrolinxSidebarCustomizer.#styleAcrolinxIFrame(iframe, timeout);
          return;
        }

        sidebarContainer.setAttribute("style", "display:block;width:100%;");
        iframe2.setAttribute("style", "width:100%;");
        appDiv.setAttribute("style", "width:100%;");
      }
    }, AcrolinxSidebarCustomizer.#DELAY);
  }
}

export default AcrolinxSidebarCustomizer;
