package nl.infosupport.intern.recognition.applicationservices;

import java.io.InputStream;

public interface EntryService {

    String register(String name);

    String newFace(String personId, InputStream image);

    String identifyPerson(InputStream inputStream);
}
