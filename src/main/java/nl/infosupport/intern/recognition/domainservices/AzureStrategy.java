package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.web.controllers.AzureException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.RenderedImage;
import java.io.IOException;

@Component
public class AzureStrategy implements RecognitionStrategy {
    private static Logger logger = LoggerFactory.getLogger(AzureStrategy.class);

    private AzureRequestHandler azureRequestHandler;

    @Autowired
    public AzureStrategy(AzureRequestHandler azureRequestHandler) {
        this.azureRequestHandler = azureRequestHandler;
    }

    @Override
    public String getName() {
        return "Azure";
    }

    @Override
    public String create(String name) {

        HttpResponse response = azureRequestHandler.performCreatePersonRequest(name);

        logger.debug("StatusCode: {}", response.getStatusLine().getStatusCode());

        try {
            var jsonResult = new JSONObject(EntityUtils.toString(response.getEntity()));

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new AzureException(jsonResult.optJSONObject("error").toString());
            }
            return jsonResult.optString("personId");

        } catch (JSONException | IOException e) {
            logger.debug("Exception", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean train() {
        HttpResponse response = azureRequestHandler.performTrainRequest();

        logger.debug("StatusCode: {}", response.getStatusLine().getStatusCode());

        try {
            var jsonResult = new JSONObject(EntityUtils.toString(response.getEntity()));

            if (response.getStatusLine().getStatusCode() != 202) {
                throw new AzureException(jsonResult.optJSONObject("error").toString());
            }
            return true;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addFace(String faceId, RenderedImage image) {

    }
}
