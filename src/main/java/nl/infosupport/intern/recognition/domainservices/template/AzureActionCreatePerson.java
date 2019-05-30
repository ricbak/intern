package nl.infosupport.intern.recognition.domainservices.template;

import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AzureActionCreatePerson extends ActionTemplate {

    private String name;

    public AzureActionCreatePerson(String name) {
        this.name = name;
    }

    @Override
    protected void setContentType() {
        this.contentType = "application/json";
    }

    @Override
    protected void buildUri() {
        uri = uriBuilder().pathSegment(GROUP, GROUPID, "persons").build();
    }

    @Override
    protected void buildRequest() {
        var httpPost = new HttpPost(uri);
        try {
            httpPost.setEntity(
                    new StringEntity(
                            new JSONObject()
                                    .put("name", name)
                                    .toString()));
        } catch (UnsupportedEncodingException | JSONException e) {
            logger.info(e.getMessage());
        }

        try {
            String entityString = EntityUtils.toString(httpPost.getEntity());

            if(!entityString.isEmpty()){
                logger.info("HttpPost body: {}", entityString);
            }

        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        this.request = httpPost;
    }

    @Override
    protected String handleRequest() {
        String response = super.handleRequest();
        logger.info("raw response {}", response);

        try {
            return new JSONObject(response).getString("personId");
        } catch (JSONException e) {
            logger.info("Exception: {}", e.getMessage());
            throw new AzureException("No person id found in response");
        }
    }
}
