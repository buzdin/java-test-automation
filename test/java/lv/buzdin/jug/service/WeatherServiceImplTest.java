package lv.buzdin.jug.service;

import com.google.common.collect.Lists;
import lv.buzdin.jug.domain.Temperature;
import lv.buzdin.jug.integration.WeatherResult;
import lv.buzdin.jug.integration.WeatherSource;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Dmitry Buzdin
 */
public class WeatherServiceImplTest {

    private WeatherServiceImpl service;

    @Before
    public void setUp() {
        service = new WeatherServiceImpl();
        service.weatherSource = mock(WeatherSource.class);
        service.entityManager = mock(EntityManager.class);
    }

    @Test
    public void testFindWeatherByCity() throws Exception {
        WeatherResult expectedResult = new WeatherResult();
        expectedResult.message = "Hello";
        expectedResult.list = Lists.newArrayList();
        WeatherResult.Item item = new WeatherResult.Item();
        expectedResult.list.add(item);
        item.name = "Kolka";
        item.main = new WeatherResult.Main();
        item.main.temp = new BigDecimal(2);

        when(service.weatherSource.findByCityName(eq("Kolka"))).thenReturn(expectedResult);

        WeatherResult result = service.findWeatherByCity("Kolka");

        verify(service.entityManager).persist(any(Temperature.class));
        assertThat(result.message, equalTo(expectedResult.message));
    }

}
