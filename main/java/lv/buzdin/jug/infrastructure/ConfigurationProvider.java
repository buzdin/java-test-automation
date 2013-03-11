package lv.buzdin.jug.infrastructure;

import com.google.common.io.Closeables;
import org.yaml.snakeyaml.Yaml;

import javax.enterprise.inject.Produces;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @author Dmitry Buzdin
 */
public class ConfigurationProvider {

    public static final String CONFIG_LOCATION = "config.yaml";
    public static final String ENV_PROPERTY = "app.env";
    public static final String DEFAULT_ENV = "production";

    @Produces
    public Configuration loadConfiguration() {
        FileInputStream inputStream = null;
        try {
            String environment = determineEnvironment();
            inputStream = getInputStream();
            return readYaml(environment, inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + CONFIG_LOCATION, e);
        } finally {
            Closeables.closeQuietly(inputStream);
        }
    }

    private String determineEnvironment() {
        String environment = System.getProperty(ENV_PROPERTY);
        if (environment == null) {
            environment = DEFAULT_ENV;
        }
        return environment;
    }

    private Configuration readYaml(String environment, FileInputStream inputStream) {
        Yaml yaml = new Yaml();
        Map<String, Map<String, String>> result = (Map<String, Map<String, String>>) yaml.load(inputStream);
        Map<String, String> properties;
        if (result.containsKey(environment)) {
            properties = result.get(environment);
        } else {
            properties = result.get(DEFAULT_ENV);
        }

        Configuration configurations = new Configuration(properties);
        return configurations;
    }

    private FileInputStream getInputStream() throws Exception {
        ClassLoader classLoader = Configuration.class.getClassLoader();
        URL resource = classLoader.getResource(CONFIG_LOCATION);
        String file = new URI(resource.toString()).getPath();
        return new FileInputStream(file);
    }

}
