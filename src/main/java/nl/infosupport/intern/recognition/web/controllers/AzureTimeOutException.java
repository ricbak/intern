package nl.infosupport.intern.recognition.web.controllers;

public class AzureTimeOutException extends RuntimeException {

    public AzureTimeOutException() {
        super("Didn't get response from Azure in time");
    }
}
