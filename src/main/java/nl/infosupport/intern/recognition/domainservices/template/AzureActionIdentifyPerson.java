package nl.infosupport.intern.recognition.domainservices.template;

import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AzureActionIdentifyPerson extends ActionTemplate {

    private String detectedFaceId;

    public AzureActionIdentifyPerson(String detectedFaceId) {
        this.detectedFaceId = detectedFaceId;
    }

    @Override
    protected void setContentType() {
        this.contentType = "application/json";
    }

    @Override
    protected void buildUri() {

        this.uri = uriBuilder()
                .pathSegment("identify")
                .build();

    }

    @Override
    protected void buildRequest() {
        var postRequest = new HttpPost(uri);

        try {

            var body = new JSONObject()
                    .put("personGroupId", GROUPID)
                    .put("faceIds", new JSONArray().put(detectedFaceId))
                    .toString();

            logger.info("Request body to Azure: {}", body);

            postRequest.setEntity(
                    new StringEntity(body));
        } catch (UnsupportedEncodingException | JSONException e) {
            logger.info(e.getMessage(),e);
        }

        this.request = postRequest;
    }

    @Override
    protected String handleRequest() {
        String response = super.handleRequest();
        logger.info("raw response {}", response);

        try {
            JSONObject firstFoundCandidate = new JSONArray(response).getJSONObject(0).getJSONArray("candidates").getJSONObject(0);
            logger.info("First found candidate: \n{}", firstFoundCandidate);
            return firstFoundCandidate.toString();
        } catch (JSONException e) {
            logger.info(e.getMessage(), e);
            throw new AzureException("Geen kandidaten gevonden");
        }
    }
}
