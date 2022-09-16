package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class YamlReader {

    public static Config config;

    public static Config getConfig() {
        return config;
    }

    public YamlReader() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            this.config = mapper.readValue(new File("src/main/resources/config.yaml"), Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
