package lv.buzdin.jug.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

/**
 * @author Dmitry Buzdin
 */
public class OpenWeather implements WeatherSource {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.1/find/name?q=";

    private final Gson gson;

    {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = builder.create();
    }

    @Inject
    HttpClient client;

    @Override
    public WeatherResult findByCityName(String name) {
        String response = client.get(BASE_URL + name);
        return gson.fromJson(response, WeatherResult.class);
    }

}
