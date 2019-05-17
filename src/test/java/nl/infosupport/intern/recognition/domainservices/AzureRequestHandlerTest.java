package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.domainservices.template.AzureActionAddFace;
import nl.infosupport.intern.recognition.domainservices.template.AzureActionDetectFace;
import nl.infosupport.intern.recognition.domainservices.template.AzureActionIdentifyPerson;
import nl.infosupport.intern.recognition.domainservices.template.AzureActionTemplate;
import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureException;
import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureTimeOutException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AzureRequestHandlerTest {

    private String subscription = "0bc91eb2c95f4b62ae746aa42884e6dc";
    private String groupId = "infosupport";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpClientFactory factory;

    @Mock
    HttpUriRequest request;

    private AzureRequestHandler requestHandler;

    @BeforeEach
    void setUp() {
        requestHandler = new AzureRequestHandler(factory);


    }

    @Test
    void createPersonRequestWithValidInputShouldReturnStatusOke() throws Exception {

        String personId = "mocked-person-id";
        when(factory.getApplicationJsonFaceApiClient()
                .execute(any())
                .getStatusLine()
                .getStatusCode())
                .thenReturn(200);
        when(factory.getApplicationJsonFaceApiClient()
                .execute(any())
                .getEntity())
                .thenReturn(new StringEntity(
                        new JSONObject()
                                .put("personId", personId)
                                .toString()));

        String result = requestHandler.handleRequest(request, "application/json");

        JSONObject jsonResult = new JSONObject(result);

        System.out.println(jsonResult.toString());
        assertThat(jsonResult.getString("personId").length(), comparesEqualTo(personId.length()));
        assertThat(jsonResult.getString("personId"), is(personId));
    }

    @Test
    void whenAddFaceWithCorrectImageShouldReturnPersistedFaceId() throws Exception {

        String persistedFaceId = "mocked-persisted-face-id";
        when(factory.getOctetStreamFaceApiClient()
                .execute(any())
                .getStatusLine()
                .getStatusCode())
                .thenReturn(200);
        when(factory.getOctetStreamFaceApiClient()
                .execute(any())
                .getEntity())
                .thenReturn(
                        new StringEntity(
                                new JSONObject()
                                        .put("persistedFaceId", persistedFaceId)
                                        .toString()));

        String  result = requestHandler.handleRequest(request, "application/octet-stream");

        JSONObject jsonResult= new JSONObject(result);

        assertThat(jsonResult.optString("persistedFaceId"), is(persistedFaceId));
        System.out.println(result);
    }

    @Test
    void trainGroupShouldReturnStatusOke() throws Exception{
        when(factory.getApplicationJsonFaceApiClient()
                .execute(any())
                .getStatusLine()
                .getStatusCode())
                .thenReturn(202);

        String result = requestHandler.handleRequest(request, "application/json");

        assertThat(result.length(), comparesEqualTo(0));

    }

    @Test
    void whenResponseIsNot200AndNot202ThenShouldThrowAzureException() throws Exception {
        when(factory.getApplicationJsonFaceApiClient()
                .execute(any())
                .getStatusLine()
                .getStatusCode())
                .thenReturn(400);
        when(factory.getApplicationJsonFaceApiClient()
                .execute(any())
                .getEntity()
                )
                .thenReturn(
                        new StringEntity(
                                new JSONObject()
                                        .put("error", "azure error")
                                        .toString()));


        Assertions.assertThrows(AzureException.class, ()-> requestHandler.handleRequest(request,"application/json"));
    }

//    @Test
//    void detectFaceInImageWithFaceShouldReturnStatusOkeAndFaceId() throws Exception{
//
//        HttpClient octetStreamFaceApiClient = HttpClients.custom().setDefaultHeaders(
//                Arrays.asList(
//                        new BasicHeader("Ocp-Apim-Subscription-Key", subscription),
//                        new BasicHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.toString())
//                )).build();
//
//        when(factory.getOctetStreamFaceApiClient()).thenReturn(octetStreamFaceApiClient);
//
//        var image = new FileInputStream(new File("src/main/resources/static/woman-face.jpg"));
//
//        var detectAction= new AzureActionDetectFace(image.readAllBytes());
//        detectAction.setAzureRequestHandler(requestHandler);
//
//        String result = detectAction.doAction();
//
//        System.out.println(result);
//    }
//
//    @Test
//    void identifyWithKnownFaceShouldReturnHighConfidence() throws Exception{
//        HttpClient jsonClient = HttpClients.custom().setDefaultHeaders(
//                Arrays.asList(
//                        new BasicHeader("Ocp-Apim-Subscription-Key", subscription),
//                        new BasicHeader("Content-Type", ContentType.APPLICATION_JSON.toString())
//                )).build();
//
//        when(factory.getApplicationJsonFaceApiClient()).thenReturn(jsonClient);
//
//        var image = new FileInputStream(new File("src/main/resources/static/woman-face.jpg"));
//
////        var action = new AzureActionIdentifyPerson("8d4613df-96a9-416c-9ce0-49891db5b4ca");
////
////        action.setAzureRequestHandler(requestHandler);
////        String confidence = action.doAction();
////
////        System.out.println(confidence);
//    }
}























