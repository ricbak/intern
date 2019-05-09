package nl.infosupport.intern.recognition.web.controllers.exceptions;

public class NoUniqueNameException extends RuntimeException {

    private final String name;

    public NoUniqueNameException(String name) {
        super("Naam is niet uniek: " + name);
        this.name = name;
    }
}
