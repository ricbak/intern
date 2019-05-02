package nl.infosupport.intern.recognition.domainservices;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class AzureRequestHandlerTest {

    @Value("${azure.subscription}")
    private String subscription = "0bc91eb2c95f4b62ae746aa42884e6dc";

    @Value("${azure.groupid}")
    private String groupId = "infosupport";

    private AzureRequestHandler requestHandler;

    @BeforeEach
    void setUp() {
        requestHandler = new AzureRequestHandler(groupId, subscription);

    }

    @Test
    void createPersonRequestWithValidInputShouldReturnStatusOke() {

        HttpResponse response = requestHandler.performCreatePersonRequest("Rico");

        System.out.println(response.getStatusLine().getStatusCode());

        assertThat(response.getStatusLine().getStatusCode(), is(200));
    }

    @Test
    void trainGroupShouldReturnStatusOke() {
        HttpResponse response = requestHandler.performTrainRequest();

        System.out.println(response.getStatusLine().getStatusCode());

        assertThat(response.getStatusLine().getStatusCode(), is(202));
    }

    @Test
    void addFaceCommandShouldReturnNewFaceId() {
        BufferedImage image = null;
        String personId = "904bcd46-aefc-4153-9d31-5d8ef07e5301";
        try {
            image = ImageIO.read(new File("C:\\Users\\ricob\\Google Drive\\School\\Informatica\\Afstuderen\\Opdracht\\Visualisatie\\woman-face.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpResponse response = requestHandler.performAddFaceRequest(personId, image);

        assertThat(response.getStatusLine().getStatusCode(), is(200));

    }
}























