package lv.buzdin.jug.functional;

import lv.buzdin.jug.domain.Temperature;
import lv.buzdin.jug.infrastructure.ConfigurationProvider;
import lv.buzdin.jug.service.WeatherService;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.*;

import javax.enterprise.context.RequestScoped;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Buzdin
 */
public class FunctionalTest {

    static CdiContainer cdiContainer;

    @BeforeClass
    public static void init() {
        System.setProperty(ConfigurationProvider.ENV_PROPERTY, "memory");

        System.setProperty("java.naming.factory.initial", "org.eclipse.jetty.jndi.InitialContextFactory");
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
    }

    @AfterClass
    public static void destroy() {
        cdiContainer.shutdown();
    }

    @Before
    public void setUp() {
        cdiContainer.getContextControl().startContext(RequestScoped.class);
    }

    @After
    public void tearDown() {
        cdiContainer.getContextControl().stopContext(RequestScoped.class);
    }

    @Test
    public void shouldGetMultipleTimes() {
        WeatherService service = BeanProvider.getContextualReference("weatherService", false, WeatherService.class);
        assertThat(service, notNullValue());
        service.findWeatherByCity("Riga");
        service.findWeatherByCity("Riga");
        service.findWeatherByCity("Riga");

        Collection<Temperature> temperatures = service.historicalMeasurements("Riga");
        assertThat(temperatures, hasSize(3));
    }

}
