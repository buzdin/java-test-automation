package lv.buzdin.jug.integration;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

import javax.enterprise.inject.Alternative;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Dmitry Buzdin
 */
@Alternative
public class MockHttpClient implements HttpClient {

    @Override
    public String get(String url) {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("response.json");
        String content = null;
        try {
            content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
        Closeables.closeQuietly(stream);

        return content;
    }

}
