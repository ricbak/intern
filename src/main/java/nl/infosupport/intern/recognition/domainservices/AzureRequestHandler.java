package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.domainservices.azure.HttpClientFactory;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@Component
public class AzureRequestHandler {
    private static Logger logger = LoggerFactory.getLogger(AzureRequestHandler.class);

    @Value("${azure.groupid}")
    private String groupId;

    @Value("${azure.subscription}")
    private String subscription;

    public HttpResponse performCreatePersonRequest(String name) {

        UriBuilder uriBuilder = new DefaultUriBuilderFactory().builder();
        URI uri = uriBuilder
                .scheme("https")
                .host("westeurope.api.cognitive.microsoft.com")
                .path("face/v1.0")
                .path("/persongroups")
                .path("/" + groupId)
                .path("/persons")
                .build();

        HttpPost httpPost = new HttpPost(uri);
        try {
            httpPost.setEntity(new StringEntity(new JSONObject().put("name", name).toString()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            logger.debug("Exception: ", e);
        }

        var httpClient = HttpClientFactory.faceApiHttpClient(subscription, APPLICATION_JSON);

        try {
            return httpClient.execute(httpPost);
        } catch (IOException e) {
            logger.debug("Exception", e);
            return new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http", 1, 1), 400, e.getMessage()));

        }
    }
}
