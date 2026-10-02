package configs;

import org.aeonbits.owner.ConfigFactory;

public final class ConfigProvider {

    private static final TestPropertiesConfig CONFIG =
            ConfigFactory.create(
                    TestPropertiesConfig.class,
                    System.getProperties()
            );

    private ConfigProvider() {
    }

    public static TestPropertiesConfig get() {
        return CONFIG;
    }
}