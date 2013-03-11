package lv.buzdin.jug.rest;

import com.google.common.annotations.VisibleForTesting;
import lv.buzdin.jug.integration.WeatherResult;
import lv.buzdin.jug.service.WeatherService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author Dmitry Buzdin
 */
@Path("weather")
@RequestScoped
@Produces(value = {MediaType.APPLICATION_JSON})
public class WeatherResource {

    @Inject
    WeatherService weatherService;

    @GET
    public WeatherDTO getWeather(@QueryParam("city") String city) {
        WeatherResult weatherResult = weatherService.findWeatherByCity(city);
        return map(weatherResult);
    }

    @VisibleForTesting
    WeatherDTO map(WeatherResult weatherResult) {
        WeatherDTO dto = new WeatherDTO();
        dto.city = weatherResult.list.get(0).name;
        dto.description = weatherResult.list.get(0).weather.get(0).description;
        dto.temperature = weatherResult.list.get(0).main.temp.toPlainString();
        dto.wind = weatherResult.list.get(0).wind.speed.toPlainString();
        return dto;
    }

}
