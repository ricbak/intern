package nl.infosupport.intern.recognition.domainservices;

import lombok.Getter;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Getter
public class HttpClientBuilder {

    private final String subscription;

    public HttpClientBuilder(@Qualifier("getAzureSubscription") String subscription) {

        this.subscription = subscription;
    }

    public HttpClient buildJsonHttpClient(){
        return HttpClients.custom().setDefaultHeaders(
                Arrays.asList(
                        new BasicHeader("Ocp-Apim-Subscription-Key", subscription),
                        new BasicHeader("Content-Type", ContentType.APPLICATION_JSON.toString())
                )).build();
    }

    public HttpClient buildOctetStreamHttpClient(){
        return HttpClients.custom().setDefaultHeaders(
                Arrays.asList(
                        new BasicHeader("Ocp-Apim-Subscription-Key", subscription),
                        new BasicHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.toString())
                )).build();
    }
}
