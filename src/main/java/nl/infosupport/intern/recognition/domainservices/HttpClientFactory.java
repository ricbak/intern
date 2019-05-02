package nl.infosupport.intern.recognition.domainservices;

import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.util.Arrays;

public class HttpClientFactory {

    private HttpClientFactory(){

    }

    public static HttpClient faceApiHttpClient(String subscriptionKey, ContentType contentType) {

        return HttpClients.custom().setDefaultHeaders(
                Arrays.asList(
                        new BasicHeader("Ocp-Apim-Subscription-Key", subscriptionKey),
                        new BasicHeader("Content-Type", contentType.toString())
                )).build();
    }
}
