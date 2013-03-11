package lv.buzdin.jug.service;

import lv.buzdin.jug.domain.Temperature;
import lv.buzdin.jug.integration.WeatherResult;
import lv.buzdin.jug.integration.WeatherSource;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Date;

/**
 * @author Dmitry Buzdin
 */
@Named("weatherService")
public class WeatherServiceImpl implements WeatherService {

    @Inject
    WeatherSource weatherSource;

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public WeatherResult findWeatherByCity(String city) {
        WeatherResult result = weatherSource.findByCityName(city);
        Temperature temperature = new Temperature();
        temperature.setDate(new Date());
        temperature.setLocation(result.list.get(0).name);
        temperature.setTemperature(result.list.get(0).main.temp);
        entityManager.persist(temperature);
        return result;
    }

    @Override
    public Collection<Temperature> historicalMeasurements(String city) {
        Query query = entityManager.createQuery("select t from Temperature t where t.location = :city");
        query.setParameter("city", city);
        return query.getResultList();
    }

}
