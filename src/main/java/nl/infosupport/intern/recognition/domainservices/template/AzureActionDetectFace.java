package nl.infosupport.intern.recognition.domainservices.template;

import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;

public class AzureActionDetectFace extends AzureActionTemplate {

    private byte[] imageBytes;

    public AzureActionDetectFace(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    protected void setContentType() {
        this.contentType = "application/octet-stream";
    }

    @Override
    protected void buildUri() {
        this.uri = uriBuilder()
                .pathSegment("detect")
                .queryParam("recognitionModel", "recognition_02")
                .build();
    }

    @Override
    protected void buildRequest() {
        var postRequest = new HttpPost(uri);

        if (imageBytes.length == 0) {
            throw new RuntimeException("Image byte array is empty!");
        }

        postRequest.setEntity(new ByteArrayEntity(imageBytes));

        request = postRequest;
    }

    @Override
    protected String handleRequest() {
        String response = super.handleRequest();
        logger.info("raw response {}", response);

        try {
            var jsonResult = new JSONArray(response);
            return jsonResult.getJSONObject(0).getString("faceId");
        } catch (JSONException e) {
            logger.info("Exception: ", e);
            throw new AzureException("No face found in image");
        }
    }
}
