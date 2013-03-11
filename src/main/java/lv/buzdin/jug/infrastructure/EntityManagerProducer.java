package lv.buzdin.jug.infrastructure;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Dmitry Buzdin
 */
public class EntityManagerProducer {

    private EntityManagerFactory emf;

    @Inject
    Configuration configuration;

    @Produces
    @RequestScoped
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }

    @PostConstruct
    public void init() {
        String persistenceUnit = configuration.getValue("db-unit");
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    protected void closeEntityManager(@Disposes EntityManager entityManager)
    {
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }

}
