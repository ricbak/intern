package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AzureRequestHandler {
    private static Logger logger = LoggerFactory.getLogger(AzureRequestHandler.class);

    private HttpClientFactory clientFactory;

    public AzureRequestHandler(HttpClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public String handleRequest(HttpUriRequest request, String contentType) {
        String response = "";

        logger.info("content-type: {}", contentType);

        HttpClient client = clientFactory.getApplicationJsonFaceApiClient();

        if (contentType.equals("application/octet-stream")) {
            client = clientFactory.getOctetStreamFaceApiClient();
        }

        try {
            logger.info("Executing post request");

            HttpResponse azureResponse = client.execute(request);

            logger.info("Response statusline: {}", azureResponse.getStatusLine());


            if (azureResponse.getEntity().getContentLength() > 2) {
                response = EntityUtils.toString(azureResponse.getEntity());
            }

            logger.info("response body: {}", response);

            if (azureResponse.getStatusLine().getStatusCode() != 200 && azureResponse.getStatusLine().getStatusCode() != 202) {
                throw new AzureException(new JSONObject(response).getString("error"));
            }

            return response;
        } catch (IOException e) {
            logger.info("IOException: ", e);
            throw new AzureException(e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
