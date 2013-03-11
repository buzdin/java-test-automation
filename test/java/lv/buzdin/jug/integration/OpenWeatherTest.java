package lv.buzdin.jug.integration;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Buzdin
 */
public class OpenWeatherTest {

    private OpenWeather weather;

    @Before
    public void setUp() {
        weather = new OpenWeather();
        weather.client = new MockHttpClient();
    }

    @Test
    public void testFindByCityName() throws Exception {
        WeatherResult result = weather.findByCityName("Riga");

        assertThat(result.list, hasSize(1));
        assertThat(result.list.get(0).sys.country, equalTo("LV"));
        assertThat(result.list.get(0).main.temp, equalTo(new BigDecimal("276.15")));
    }

}
