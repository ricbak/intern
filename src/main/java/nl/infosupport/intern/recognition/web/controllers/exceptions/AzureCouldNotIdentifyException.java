package nl.infosupport.intern.recognition.web.controllers.exceptions;

public class AzureCouldNotIdentifyException extends RuntimeException {
    public AzureCouldNotIdentifyException(String message) {
        super(message);
    }
}
