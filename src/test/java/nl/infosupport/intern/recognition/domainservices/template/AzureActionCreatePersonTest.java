package nl.infosupport.intern.recognition.domainservices.template;

import nl.infosupport.intern.recognition.domainservices.AzureRequestHandler;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.ImageIO;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AzureActionCreatePersonTest {

    @Mock
    private AzureRequestHandler requestHandler;

    @Test
    void createPersonInAzureShouldReturnPersonId() throws Exception {
        String personId = "057f0f75-cac7-446b-8ae0-0e3559b245dc";
        String azureResponse = new JSONObject().put("personId", personId).toString();

        when(requestHandler.handleRequest(any(),any()))
                .thenReturn(azureResponse);

        var action = new AzureActionCreatePerson("test-name");
        action.setAzureRequestHandler(requestHandler);

        String result = action.doAction();

        assertThat(result, is(personId));

        System.out.println(result);
    }
}