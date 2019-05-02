package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.web.models.Person;

import java.util.List;

public interface EntryService {

    Person.SavedPerson register(String name);
    default List<nl.infosupport.intern.recognition.domain.Person> listPersons(){
        return null;
    }
}
