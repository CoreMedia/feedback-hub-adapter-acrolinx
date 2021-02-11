package com.coremedia.labs.plugins.feedbackhub.acrolinx.itemtypes {
public class AcrolinxSidebarCustomizer {
  private static const DELAY:Number = 200;

  public static function styleAcrolinx(targetElement:*):void {
    //mmh, some custom styling for the iframe
    var iframe:* = targetElement.getElementsByTagName('iframe')[0];
    if (iframe) {
      iframe.style = "height:100%;height:100%;width:100%;border:1px solid #CCC;background-color:#FFF;";
      styleAcrolinxIFrame(iframe, 10000);
    }
  }

  private static function styleAcrolinxIFrame(iframe:*, timeout:Number):void {
    if (timeout < 0) {
      trace('[WARN]', 'Failed to apply custom styling for Acrolinx sidebar.');
      return;
    }

    timeout = timeout - DELAY;
    window.setTimeout(function ():void {
      if (!iframe.contentDocument) {
        styleAcrolinxIFrame(iframe, timeout);
        return;
      }

      var sidebarContainer:* = iframe.contentDocument.getElementById('sidebarContainer');
      if (!sidebarContainer) {
        styleAcrolinxIFrame(iframe, timeout);
        return;
      }

      var iframe2:* = sidebarContainer.firstChild;
      if (iframe2 && iframe2.contentDocument) {
        var appDiv:* = iframe2.contentDocument.getElementById('app');
        if (!appDiv) {
          styleAcrolinxIFrame(iframe, timeout);
          return;
        }

        sidebarContainer.setAttribute("style", "display:block;width:100%;");
        iframe2.setAttribute("style", "width:100%;");
        appDiv.setAttribute("style", "width:100%;");
      }
    }, DELAY);
  }
}
}