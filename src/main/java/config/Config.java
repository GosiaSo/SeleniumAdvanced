package config;

import config.models.Browser;
import config.models.Environment;

public class Config {

    public Environment environment;
    public Browser browser;

    public Environment getEnvironment() {
        return environment;
    }

    public Browser getBrowser() {
        return browser;
    }
}
