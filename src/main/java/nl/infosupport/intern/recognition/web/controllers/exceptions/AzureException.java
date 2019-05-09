package nl.infosupport.intern.recognition.web.controllers.exceptions;

public class AzureException extends RuntimeException {
    public AzureException(String message) {
        super(message);
    }
}
