package configs;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties",
        "classpath:default.properties"
})
public interface TestPropertiesConfig extends Config {

    @Key("baseUrl")
    String getApiBaseUrl();

    @Key("uiBaseUrl")
    String getUiBaseUrl();

    @Key("email")
    String getEmail();

    @Key("password")
    String getPassword();
}