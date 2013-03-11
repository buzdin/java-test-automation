package lv.buzdin.jug.integration;

import com.sun.jersey.api.client.Client;

/**
 * @author Dmitry Buzdin
 */
public class JerseyHttpClient implements HttpClient {

    @Override
    public String get(String url) {
        Client jersey = Client.create();
        return jersey.resource(url).get(String.class);
    }

}
