package lv.buzdin.jug.api;

import lv.buzdin.jug.infrastructure.ConfigurationProvider;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.rules.ExternalResource;

import javax.naming.Reference;

/**
 * @author Dmitry Buzdin
 */
public class EmbeddedJetty extends ExternalResource {

    public static final int PORT = 9999;

    private static String[] __dftConfigurationClasses =
            {
                    "org.eclipse.jetty.webapp.WebInfConfiguration",
                    "org.eclipse.jetty.webapp.WebXmlConfiguration",
                    "org.eclipse.jetty.webapp.MetaInfConfiguration",
                    "org.eclipse.jetty.webapp.FragmentConfiguration",
                    "org.eclipse.jetty.plus.webapp.EnvConfiguration",
                    "org.eclipse.jetty.webapp.JettyWebXmlConfiguration"
            };

    static Server server;

    @Override
    protected void before() throws Throwable {
        if (server == null) {
            init();
        }
    }

    public static void main(String[] args) throws Exception {
        init();
    }

    private static void init() throws Exception {
        System.setProperty(ConfigurationProvider.ENV_PROPERTY, "memory");
        System.setProperty("java.naming.factory.url", "org.eclipse.jetty.jndi");
        System.setProperty("java.naming.factory.initial", "org.eclipse.jetty.jndi.InitialContextFactory");

        server = new Server(PORT);
        HandlerList handlerList = new HandlerList();

        WebAppContext webApp = new WebAppContext();
        webApp.setConfigurationClasses(__dftConfigurationClasses);
        webApp.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        webApp.setResourceBase("src/main/webapp");
        webApp.setContextPath("/");
        webApp.setClassLoader(Thread.currentThread().getContextClassLoader());

        ServletContextHandler servletContextHandler;
        servletContextHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/webapp");

        new Resource("BeanManager", new Reference("javax.enterprise.inject.spi.BeanMnanager",
                "org.jboss.weld.resources.ManagerObjectFactory", null));

        handlerList.addHandler(servletContextHandler);
        handlerList.addHandler(resourceHandler);
        handlerList.addHandler(webApp);
        server.setHandler(handlerList);
        server.start();
    }

    public void shutdown() throws Exception {
        server.stop();
    }

}
