# Adapter Configuration

The configuration of the Acrolinx adapter consist of a settings documents that can be
put in a global or site-specific folders.

- Global: _/Settings/Options/Settings/Feedback Hub/_
- Site specific: _&lt;SITE&gt;/Options/Settings/Feedback Hub/_


## General Settings

The adapter settings can be configured on a global or site specific level. The following
XML shows an example configuration.

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
      <StringProperty Name="observedProperties">detailText</StringProperty>
      <StructProperty Name="settings">
        <Struct>
          <StringProperty Name="serverAddress">YOUR_SERVER_ADDRESS_WITHOUT_PROTOCOL</StringProperty>
          <StringProperty Name="clientSignature">YOUR_SIGNATURE</StringProperty>
          <StringProperty Name="accessToken">YOUR_ACCESS_TOKEN</StringProperty>
          <StringProperty Name="propertyNames">title,detailText,teaserText</StringProperty>
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

Ensure that the _reloadMode_ is set to _none_. This ensures that the Feedback tab panel
is not re-rendered when a content change is made. Since the Acrolinx detects changes inside
textfield by itself, we don't wont the sidebar to be reloaded.

Every _settings_ sub-struct contains the following properties:

| Property          | Description   |
| ----------------- | ------------- |
| serverAddress     | The Acrolinx server to work on, without 'https' prefix. |
| accessToken       | The access token, taken from the Acrolinx configuration. This can also be a user specific one and configured on the dashboard. |
| propertyNames     | The text based content properties to analyse, invalid ones are ignored. |
| profileMapping    | Defines a mapping from CoreMedia sites to Acrolinx guidance profiles. |

The _accessToken_ property doesn't have to be necessarily configured within the settings document.
It is used to retrieve the list of guidance profiles from Acrolinx to allow a predefined mapping from CoreMedia sites
to these profiles (see next section).

#### Acrolinx Guidance Profiles

A profile mapping key can be a CoreMedia site id or a fallback language.
The value of the profile mapping is the name or id of the Acrolinx guidance profile.
If you website only supports one profile, you can simply map this profile name or id with the key value _default_.
If no mapping is defined or a matching profile is not found, the options menu inside the Acrolinx sidebar is enabled and the
user can select the profile by themself.


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