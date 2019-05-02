package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.domain.Person;
import nl.infosupport.intern.recognition.domainservices.azure.CreatePersonService;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import nl.infosupport.intern.recognition.web.controllers.NoUniqueNameException;
import nl.infosupport.intern.recognition.web.models.Person.SavedPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class AzureEntryService implements EntryService {

    private static Logger logger = LoggerFactory.getLogger(AzureEntryService.class);


    private PersonRepositoryAdapter repo;
    private CreatePersonService createPersonService;

    @Autowired
    public AzureEntryService(PersonRepositoryAdapter repo,
                             @Qualifier("getAzureCreatePersonService") CreatePersonService createPersonService) {

        this.repo = repo;
        this.createPersonService = createPersonService;
    }

//    @Override
    public SavedPerson register(String name) {

        logger.debug("{}", Thread.currentThread().getName());

        var uniqueName = repo.isUniqueName(name)
                .orElseThrow(() -> new NoUniqueNameException(name));

        var azurePersonIdFuture = CompletableFuture
                .supplyAsync(() -> createPersonService.createPerson(uniqueName));


        String personId = repo.create(name, azurePersonIdFuture, "");

        var savedPerson = new SavedPerson(name, personId, "");

        return savedPerson;

    }

//    @Override
    public List<Person> listPersons(){
        return repo.findAllPersons();
    }
}
