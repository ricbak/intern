package nl.infosupport.intern.recognition.domainservices.template;

import org.apache.http.client.methods.HttpPost;

public class AzureActionTrain extends ActionTemplate {

    @Override
    protected void setContentType() {
        this.contentType = "application/json";
    }

    @Override
    protected void buildUri() {
        this.uri = uriBuilder()
                .pathSegment(GROUP, GROUPID, "train")
                .build();
    }

    @Override
    protected void buildRequest() {
        this.request = new HttpPost(uri);
    }
}
