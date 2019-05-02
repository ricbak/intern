package nl.infosupport.intern.recognition.web.controllers;

import nl.infosupport.intern.recognition.applicationservices.EntryService;
import nl.infosupport.intern.recognition.domain.Person;
import nl.infosupport.intern.recognition.domainservices.azure.actions.group.TrainGroupCommandHandler;
import nl.infosupport.intern.recognition.web.models.Person.NewPerson;
import nl.infosupport.intern.recognition.web.models.Person.SavedPerson;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping(path = "/person")
@CrossOrigin
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(TrainGroupCommandHandler.class);

    private final EntryService entryService;

    @Autowired
    public PersonController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping(path = "/register")
    public String registerPerson(@RequestBody NewPerson person) {

        logger.debug("{}", person);

        try {
            SavedPerson result = entryService.register(person.getName());

            return result.getName();
        }
        catch (NoUniqueNameException exc){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage());
        }
        catch (AzureTimeOutException exc) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, exc.getMessage());
        }
    }

    @GetMapping(path = "/persons")
    public String listPersons(){
        List<Person> people = entryService.listPersons();

        JSONArray jsonArray = new JSONArray();

        people.forEach(jsonArray::put);

        return jsonArray.toString();
    }

}
