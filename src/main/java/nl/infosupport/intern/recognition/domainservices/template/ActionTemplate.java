package nl.infosupport.intern.recognition.domainservices.template;

import nl.infosupport.intern.recognition.domainservices.AzureRequestHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

public abstract class ActionTemplate {
    protected static Logger logger = LoggerFactory.getLogger(ActionTemplate.class);

    private AzureRequestHandler azureRequestHandler;
    protected String contentType;
    protected static final String GROUP = "persongroups";
    protected static final String GROUPID = "infosupport";

    private boolean handlerSet = false;

    protected URI uri;
    protected HttpUriRequest request;

    public String doAction() {

        if (!handlerSet) {
            throw new RuntimeException("Handler not set!");
        }

        setContentType();
        buildUri();
        buildRequest();
        return handleRequest();
    }

    protected abstract void setContentType();

    protected abstract void buildUri();

    protected abstract void buildRequest();

    protected String handleRequest() {
        return azureRequestHandler.handleRequest(request, contentType);
    }

    public void setAzureRequestHandler(AzureRequestHandler azureRequestHandler) {
        this.azureRequestHandler = azureRequestHandler;
        handlerSet = true;
    }

    protected UriBuilder uriBuilder() {
        return new DefaultUriBuilderFactory("https://westeurope.api.cognitive.microsoft.com/face/v1.0")
                .builder();
    }
}
