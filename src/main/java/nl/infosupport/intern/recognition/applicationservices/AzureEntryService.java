package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.domainservices.azure.CreatePersonService;
import nl.infosupport.intern.recognition.domainservices.azure.actions.group.TrainGroupCommandHandler;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AzureEntryService implements EntryService {

    private static Logger logger = LoggerFactory.getLogger(TrainGroupCommandHandler.class);


    private PersonRepositoryAdapter personRepositoryService;
    private CreatePersonService createPersonService;

    @Autowired
    public AzureEntryService(PersonRepositoryAdapter personRepositoryService,
                             @Qualifier("getAzureCreatePersonService") CreatePersonService createPersonService) {

        this.personRepositoryService = personRepositoryService;
        this.createPersonService = createPersonService;
    }

    @Override
    public RegisterResult register(String name) {

        //If the name already exists, handle that somehow.
        if (!personRepositoryService.isUniqueName(name)) {
            return new RegisterResult(false, name, "No unique name");
        }

        logger.debug("{}", Thread.currentThread().getName());

        var azurePersonIdFuture = CompletableFuture
                .supplyAsync(() -> createPersonService.createPerson(name));

        String result = personRepositoryService.create(name, azurePersonIdFuture);

        return new RegisterResult(true, name, result);

    }
}
