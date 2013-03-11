package lv.buzdin.jug.service;

import lv.buzdin.jug.domain.Temperature;
import lv.buzdin.jug.integration.WeatherResult;

import java.util.Collection;

/**
 * @author Dmitry Buzdin
 */
public interface WeatherService {

    WeatherResult findWeatherByCity(String city);

    Collection<Temperature> historicalMeasurements(String city);

}
