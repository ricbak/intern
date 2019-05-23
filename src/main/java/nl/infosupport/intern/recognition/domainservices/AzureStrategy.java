package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.domainservices.template.ActionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AzureStrategy implements RecognitionStrategy {
    private static Logger logger = LoggerFactory.getLogger(AzureStrategy.class);

    private AzureRequestHandler azureRequestHandler;

    @Autowired
    public AzureStrategy(AzureRequestHandler azureRequestHandler) {
        this.azureRequestHandler = azureRequestHandler;
    }

    @Override
    public String getStrategyId() {
        return "Azure";
    }

    @Override
    public String performAction(ActionTemplate actionTemplate){

        logger.info("set AzureRequestHandler with: {}", azureRequestHandler);
        actionTemplate.setAzureRequestHandler(azureRequestHandler);
        return actionTemplate.doAction();
    }
}
