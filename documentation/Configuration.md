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
          <StringProperty Name="serverAddress">YOUR_SERVER_ADDRESS_WITHOUT_PROTOCOL</StringProperty>
          <StringProperty Name="clientSignature">YOUR_CLIENT_SIGNATURE</StringProperty>
          <StringProperty Name="accessToken">YOUR_ACCESS_TOKEN</StringProperty>
          <StringProperty Name="propertyNames">detailText</StringProperty>
          <StructProperty Name="profileMapping">
            <Struct>
              <StringProperty Name="abffe57734feeee">en-Marketing US</StringProperty>
              <StringProperty Name="de">de-Standard</StringProperty>
              <StringProperty Name="en">en-Standard US</StringProperty>
            </Struct>
          </StructProperty>
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
| accessToken       | The access token, can be retrieved through the Acrolinx dashboard. |
| propertyNames     | The text based content properties to analyse, invalid ones are ignored. |
| profileMapping    | Defines a mapping from CoreMedia sites to Acrolinx guidance profiles. |

#### Acrolinx Guidance Profiles

A profile mapping key can be a CoreMedia site id or a fallback language.
The value of the profile mapping is the name or id of the Acrolinx guidance profile.
If you website only supports one profile, you can simply map this profile by using the key value _default_.
If no mapping is defined or a matching profile is not found, the options menu inside the Acrolinx sidebar are enabled and the 
user has to select the profile by themself.


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

## Acrolinx Sidebar Language

The language of the Acrolinx sidebar is determined through the language set for the Studio.
At the time of this integration, Acrolinx supports all languages that are supported by the Studio too.