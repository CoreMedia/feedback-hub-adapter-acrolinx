# Adapter Configuration

The configuration of the adpater consist of a settings documents that can be
put in a global or site-specific folders.

- Global: _/Settings/Options/Settings/Feedback Hub/_
- Site specific: _&lt;SITE&gt;/Options/Settings/Feedback Hub/_


## General Settings

The adapter settings can be configured on a global or site specific level. The following
XML shows an example configuration. If you don't know how to get your client signature,
please visit https://www.acrolinx.com/ for more detailed instructions.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<CMSettings folder="/Settings/Options/Settings/Feedback Hub" name="Acrolinx Adapter (Global)">
  <locale></locale>
  <master>
  </master>
  <settings>
    <Struct xmlns="http://www.coremedia.com/2008/struct">
      <StringProperty Name="factoryId">acrolinx</StringProperty>
      <StringProperty Name="groupId">acrolinx</StringProperty>
      <StringProperty Name="contentTypes">CMArticle</StringProperty>
      <StringProperty Name="reloadMode">none</StringProperty>
      <BooleanProperty Name="enabled">true</BooleanProperty>
      <StructProperty Name="settings">
        <Struct>
          <StringProperty Name="serverAddress">YOUR_SERVER_ADDRESS</StringProperty>
          <StringProperty Name="clientSignature">YOUR_CLIENT_SIGNATURE</StringProperty>
          <StringProperty Name="propertyNames">detailText</StringProperty>
        </Struct>
      </StructProperty>
    </Struct>
  </settings>
  <identifier></identifier>
</CMSettings>

```


Every _settings_ sub-struct contains the following properties:

| Property          | Description   |
| ----------------- | ------------- |
| serverAddress     | The Acrolinx server to work on, without 'https' prefix. |
| clientSignature   | The client signature used for API authentication. |
| propertyNames     | The text based content properties to analyse, invalid ones are ignored. |


## Security Settings

In order to allow the Acrolinx Sidebar to be loaded and communicate with the server, different 
Studio CSP settings have to be adapted:  

```properties
studio.security.csp.imgSrc='self',data:,http:,https:
studio.security.csp.script-src='unsafe-eval','unsafe-inline','self',data:,http:,https:,https://unpkg.com/
studio.security.csp.style-src='unsafe-inline','self',data:,http:,https://<YOUR_ACROLINX_SERVER>/
studio.security.csp.font-src='self',data:,http:,https:,https://unpkg.com/
studio.security.csp.connect-src='self',https://<YOUR_ACROLINX_SERVER>/
```