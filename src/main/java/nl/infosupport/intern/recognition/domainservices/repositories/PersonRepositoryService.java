package nl.infosupport.intern.recognition.domainservices.repositories;

import nl.infosupport.intern.recognition.domain.Person;
import nl.infosupport.intern.recognition.domainservices.azure.actions.group.TrainGroupCommandHandler;
import nl.infosupport.intern.recognition.web.controllers.AzureTimeOutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class PersonRepositoryService implements PersonRepositoryAdapter {

    private static Logger logger = LoggerFactory.getLogger(TrainGroupCommandHandler.class);

    private PersonRepository repo;

    @Autowired
    public PersonRepositoryService(PersonRepository personRepository) {
        this.repo = personRepository;
    }

    @Override
    public Optional<String> isUniqueName(String name) {

        if(!repo.existsByName(name)){
            return Optional.of(name);
        }

        return Optional.empty();
    }

    @Override
    public String create(String name, CompletableFuture<String> personId) {

        Person person = new Person();
        person.setName(name);

        try {
            String fetchedPersonIdFromAzure = personId.get(5, SECONDS);
            person.setAzureId(fetchedPersonIdFromAzure);
            repo.save(person);
            logger.debug("entity saved in database");
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            logger.debug("Exception: ", e);
        } catch (TimeoutException e) {
            throw new AzureTimeOutException();
        }


        //Need to throw an exception instead of dealing with the exception here because
        //now I can't deal with the exception in the controller
//        personId.orTimeout(5, SECONDS)
//                .exceptionally((throwable) -> "Time-Out")
//                .thenAcceptAsync(person::setAzureId)
//                .thenRun(() -> repo.save(person))
//                .thenRun(() -> logger.debug("entity saved in database"));


        return person.getAzureId();
    }


}
