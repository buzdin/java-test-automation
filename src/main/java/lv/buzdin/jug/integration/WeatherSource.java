package lv.buzdin.jug.integration;

/**
 * @author Dmitry Buzdin
 */
public interface WeatherSource {

    WeatherResult findByCityName(String name);

}
