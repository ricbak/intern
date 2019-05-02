package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.web.models.Person.SavedPerson;

import java.awt.image.RenderedImage;
import java.util.List;

public interface EntryService {

    SavedPerson register(String name);

    boolean train();

    boolean newFace(String personId, RenderedImage image);

    default List<nl.infosupport.intern.recognition.domain.Person> listPersons(){
        return null;
    }
}
