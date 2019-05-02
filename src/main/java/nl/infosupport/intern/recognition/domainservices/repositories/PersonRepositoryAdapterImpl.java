package nl.infosupport.intern.recognition.domainservices.repositories;

import nl.infosupport.intern.recognition.domain.Person;
import nl.infosupport.intern.recognition.web.controllers.AzureTimeOutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
public class PersonRepositoryAdapterImpl implements PersonRepositoryAdapter {

    private static Logger logger = LoggerFactory.getLogger(PersonRepositoryAdapterImpl.class);

    private PersonRepository repo;

    public PersonRepositoryAdapterImpl(PersonRepository personRepository) {
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
    public String create(String name, CompletableFuture<String> personId, String source) {

        Person person = new Person();
        person.setName(name);
        person.setSource(source);

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

    @Override
    public List<Person> findAllPersons() {
        var list = new ArrayList<Person>();
        repo.findAll().forEach(list::add);

        return list;
    }
}
