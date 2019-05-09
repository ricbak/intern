package nl.infosupport.intern.recognition.web.controllers.exceptions;

public class ImageFormatException extends RuntimeException {
    public ImageFormatException(String message) {
        super(message);
    }
}
