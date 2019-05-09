package nl.infosupport.intern.recognition.web.controllers;

import nl.infosupport.intern.recognition.domain.Person;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
@CrossOrigin
public class AdminController {

    private PersonRepository repo;

    @Autowired
    public AdminController(PersonRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = "/person/list")
    public Iterable<Person> listPersons(){
        Iterable<Person> all = repo.findAll();

        return all;
    }
}
