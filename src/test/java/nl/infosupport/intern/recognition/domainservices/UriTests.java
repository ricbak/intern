package nl.infosupport.intern.recognition.domainservices;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;

public class UriTests {

    @Test
    void UriBuilderTests() {
        try {
            URI uri = new URI("https", "westeurope.api.cognitive.microsoft.com", "/path", null);
            System.out.println(uri.toString());

            HttpClient httpClient = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder(uri).POST()
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void jsonObject() throws JSONException {
        JSONObject put = new JSONObject().put("name", "Rico");
        System.out.println(put.toString());
    }

    @Test
    void testLogger() {
        Logger logger = LoggerFactory.getLogger(UriTests.class);

        logger.debug("Variable: {}", "a variable");
    }
}