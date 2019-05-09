package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.domainservices.template.AzureActionTemplate;

public interface RecognitionStrategy {

    String getStrategyId();
    String performAction(AzureActionTemplate azureActionTemplate);
}
