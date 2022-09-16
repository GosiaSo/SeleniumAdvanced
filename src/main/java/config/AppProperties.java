package config;

import config.models.Browser;
import config.models.EnvironmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class AppProperties {
    private static final Logger logger = LoggerFactory.getLogger(AppProperties.class);

    YamlReader yamlReader = new YamlReader();
    private Browser browser;
    private List<EnvironmentModel> environmentModels;

    public AppProperties() {
        setBrowserProperties();
        setEnvironmentProperties();
    }

    public static AppProperties getInstance() {
        return AppProperties.AppPropertiesSingleton.INSTANCE;
    }

    private void setEnvironmentProperties() {
        environmentModels = yamlReader.getConfig().getEnvironment().getListOfEnvironments();
        boolean foundActiveEnvironment = false;

        for (EnvironmentModel environmentModel : environmentModels) {
            if (environmentModel.isActive()) {
                foundActiveEnvironment = true;
                Map<String, Object> environmentProperties = environmentModel.getProperties();
                for (Map.Entry<String, Object> stringObjectEntry : environmentProperties.entrySet()) {
                    String key = stringObjectEntry.getKey();
                    String value = stringObjectEntry.getValue().toString();
                    System.setProperty(key, value);
                    logger.info("Loaded env properties: {} = {}", key, value);
                }
            }
        }
    }

    private void setBrowserProperties() {
        browser = yamlReader.getConfig().getBrowser();
        Map<String, Object> browserProperties = browser.getBrowserProperties();
        for (Map.Entry<String, Object> stringObjectEntry : browserProperties.entrySet()) {
            String key = stringObjectEntry.getKey();
            String value = stringObjectEntry.getValue().toString();
            System.setProperty(key, value);
            logger.info("Loaded browser properties: {} = {}", key, value);
        }
    }

    private static class AppPropertiesSingleton {
        private static final AppProperties INSTANCE = new AppProperties();
    }
}
