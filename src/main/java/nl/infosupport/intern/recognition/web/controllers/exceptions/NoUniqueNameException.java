package nl.infosupport.intern.recognition.web.controllers.exceptions;

public class NoUniqueNameException extends RuntimeException {

    public NoUniqueNameException(String name) {
        super("Naam is niet uniek: " + name);
    }
}
