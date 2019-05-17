package nl.infosupport.intern.recognition.web.controllers;

import nl.infosupport.intern.recognition.domain.Person;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepository;
import nl.infosupport.intern.recognition.web.models.Person.SavedPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/admin")
@CrossOrigin
public class AdminController {

    private PersonRepository repo;

    @Autowired
    public AdminController(@Qualifier("PersonRepository")PersonRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = "/person/list")
    public Iterable<SavedPerson> listPersons(){
        Iterable<Person> all = repo.findAll();
        ArrayList<SavedPerson> allReact = new ArrayList<>();
        all.forEach(p -> allReact.add(new SavedPerson(p.getId(),p.getName(),p.getAzureId(),p.getSource())));
        return allReact;
    }
}
