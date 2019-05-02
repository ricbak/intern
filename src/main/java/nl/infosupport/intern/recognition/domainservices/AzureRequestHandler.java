package nl.infosupport.intern.recognition.domainservices;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.APPLICATION_OCTET_STREAM;

@Component
public class AzureRequestHandler {
    private static Logger logger = LoggerFactory.getLogger(AzureRequestHandler.class);

    @Value("${azure.groupid}")
    private String groupId;

    @Value("${azure.subscription}")
    private String subscription;

    public AzureRequestHandler(@Qualifier("getAzureGroupId") String groupId,
                               @Qualifier("getAzureSubscription") String subscription) {
        this.groupId = groupId;
        this.subscription = subscription;
    }

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

    public HttpResponse performTrainRequest(){
        UriBuilder uriBuilder = new DefaultUriBuilderFactory().builder();
        URI uri = uriBuilder
                .scheme("https")
                .host("westeurope.api.cognitive.microsoft.com")
                .path("face/v1.0")
                .path("/persongroups")
                .path("/" + groupId)
                .path("/train")
                .build();

        var httpClient = HttpClientFactory.faceApiHttpClient(subscription, APPLICATION_JSON);
        var httpPost = new HttpPost(uri);

        try {
            return httpClient.execute(httpPost);
        } catch (IOException e) {
            logger.debug("Exception: ", e);
            return new BasicHttpResponse(
                    new BasicStatusLine(
                            new ProtocolVersion("http", 1, 1), 400, e.getMessage()));
        }
    }

    public HttpResponse performAddFaceRequest(String personId, RenderedImage image){
        UriBuilder uriBuilder = new DefaultUriBuilderFactory().builder();
        URI uri = uriBuilder
                .scheme("https")
                .host("westeurope.api.cognitive.microsoft.com")
                .path("face/v1.0")
                .path("/persongroups")
                .path("/" + groupId)
                .path("/persons")
                .path("/" + personId)
                .path("/persistedFaces")
                .build();

        try {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);

            var httpClient = HttpClientFactory.faceApiHttpClient(subscription, APPLICATION_OCTET_STREAM);

            var httpPost = new HttpPost(uri);
            httpPost.setEntity(new ByteArrayEntity(byteArrayOutputStream.toByteArray()));

            return httpClient.execute(httpPost);

        } catch (IOException e) {
            logger.debug("Exception", e);
            return new BasicHttpResponse(
                    new BasicStatusLine(
                            new ProtocolVersion("http", 1, 1), 400, e.getMessage()));

        }
    }
}
