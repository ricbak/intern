package nl.infosupport.intern.recognition.domainservices.template;

import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class AzureActionAddFace extends AzureActionTemplate {

    private String personId;
    private byte[] imageBytes;

    public AzureActionAddFace(String personId, byte[] imageBytes) {
        this.personId = personId;
        this.imageBytes = imageBytes;
    }

    @Override
    protected void setContentType() {
        this.contentType = "application/octet-stream";
    }

    @Override
    protected void buildUri() {
        if (personId.isEmpty()) {
            throw new RuntimeException("Person-id is not set! first call method: 'setPersonId'");
        }

        this.uri = uriBuilder()
                .pathSegment(GROUP, GROUPID, "persons", personId, "persistedFaces").build();
    }

    @Override
    protected void buildRequest() {
        var postRequest = new HttpPost(uri);

        if (imageBytes.length == 0) {
            throw new RuntimeException("Image is not set! first call method: 'setImage'");
        }

        postRequest.setEntity(new ByteArrayEntity(imageBytes));

        this.request = postRequest;
    }

    @Override
    protected String handleRequest() {
        String response = super.handleRequest();
        logger.info("raw response {}", response);

        try {
            return new JSONObject(response).getString("persistedFaceId");
        } catch (JSONException e) {
            logger.info("Exception: ", e);
            throw new AzureException("No persisted face id found in response");
        }
    }
}
