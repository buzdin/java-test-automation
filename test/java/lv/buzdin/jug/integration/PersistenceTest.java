package lv.buzdin.jug.integration;

import lv.buzdin.jug.domain.Temperature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Buzdin
 */
public class PersistenceTest {

    EntityManagerFactory emf;
    EntityManager em;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("h2-unit");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void shouldPersist() throws Exception {
        EntityTransaction trx = em.getTransaction();

        trx.begin();
        Temperature entity = new Temperature();
        em.persist(entity);
        trx.commit();

        Long id = entity.getId();
        trx.begin();
        Temperature temperature = em.find(Temperature.class, id);
        assertThat(temperature, notNullValue());
        trx.commit();
    }

}
