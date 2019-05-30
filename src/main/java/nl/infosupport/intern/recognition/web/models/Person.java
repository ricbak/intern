package nl.infosupport.intern.recognition.web.models;

import lombok.Data;

public class Person {

    private Person(){

    }

    @Data
    public static class SavedPerson {
        private final long id;
        private final String name;
        private final String azureId;
        private final String source;

    }

    @Data
    public static class NewPerson {

        private String name;
    }
}

