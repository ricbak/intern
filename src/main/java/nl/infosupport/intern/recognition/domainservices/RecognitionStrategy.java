package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.domainservices.template.ActionTemplate;

public interface RecognitionStrategy {

    String getStrategyId();
    String performAction(ActionTemplate actionTemplate);
}
