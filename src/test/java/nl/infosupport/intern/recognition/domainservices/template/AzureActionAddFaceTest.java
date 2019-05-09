package nl.infosupport.intern.recognition.domainservices.template;

import nl.infosupport.intern.recognition.domainservices.AzureRequestHandler;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AzureActionAddFaceTest {

    @Mock
    private AzureRequestHandler requestHandler;

    @Test
    void addFaceShouldAddFaceInAzure() throws Exception {

        String persistedFaceId = "057f0f75-cac7-446b-8ae0-0e3559b245dc";
        String azureResponse = new JSONObject().put("persistedFaceId", persistedFaceId).toString();

        when(requestHandler.handleRequest(any(), any()))
                .thenReturn(azureResponse);

        var image = new FileInputStream(new File("src/main/resources/static/woman-face.jpg"));

        AzureActionAddFace action = new AzureActionAddFace(persistedFaceId, image.readAllBytes());
        action.setAzureRequestHandler(requestHandler);

        String result = action.doAction();

        assertThat(result, is(persistedFaceId));
        System.out.println(result);
    }

    @Test
    void readInputStreamTwice() throws Exception {
        var image = new FileInputStream(new File("src/main/resources/static/woman-face.jpg"));

        byte[] bytes = image.readAllBytes();

        System.out.println(bytes.length);
        System.out.println(bytes.length);


    }
}