package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.web.controllers.AzureException;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AzureStrategyTest {

    @Mock
    AzureRequestHandler requestHandler;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        when(requestHandler.performCreatePersonRequest(any()))
                .thenReturn(httpResponse);
    }

    @Test
    void createPersonWithValidResponseShouldExtractPersonIdAndReturn() throws Exception {
        var validName = "valid name";
        var validId = "904bcd46-aefc-4153-9d31-5d8ef07e5301";
        var strategy = new AzureStrategy(requestHandler);

        when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200);

        when(httpResponse.getEntity())
                .thenReturn(
                        new StringEntity(
                                new JSONObject()
                                        .put("personId", validId).toString()));

        String azurePersonId = strategy.create(validName);

        assertThat(azurePersonId.length(), is(36));
    }

    @Test
    void createPersonWithInvalidResponseShouldThrowRuntimeExceptionWithAzureMessage() throws Exception {
        var invalidName = "invalid-name";
        var strategy = new AzureStrategy(requestHandler);

        when(httpResponse.getStatusLine().getStatusCode()).thenReturn(400);

        var jsonObject = new JSONObject()
                .put("error", new JSONObject().
                        put("message", "error"));

        when(httpResponse.getEntity())
                .thenReturn(
                        new StringEntity(jsonObject.toString()));

        when(requestHandler.performCreatePersonRequest(invalidName))
                .thenReturn(httpResponse);

        AzureException azureException= Assertions.assertThrows(AzureException.class, () -> strategy.create(invalidName));
        assertThat(azureException.getMessage(), is(jsonObject.getString("error").toString()));


    }
}