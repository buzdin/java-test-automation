package lv.buzdin.jug.api;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Dmitry Buzdin
 */
public class RestApiTest {

    @Rule
    public EmbeddedJetty jetty = new EmbeddedJetty();

    @Before
    public void setUp() {
        RestAssured.port = EmbeddedJetty.PORT;
        RestAssured.basePath = "/";
    }

    @Test
    public void shouldReturnWeather() {
        expect()
                .body("description", equalTo("broken clouds"))
                .body("temperature", equalTo("276.15"))
                .body("wind", equalTo("4.1"))
                .body("city", equalTo("Riga"))
                .statusCode(200)
        .when()
                .get("weather?city=Riga");
    }

}
