package lv.buzdin.jug.infrastructure;

import java.util.Map;

/**
 * @author Dmitry Buzdin
 */
public class Configuration {

    private final Map<String, String> properties;

    public Configuration(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getValue(String name) {
        return properties.get(name);
    }

}
