package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.domainservices.RecognitionStrategy;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import nl.infosupport.intern.recognition.web.controllers.NoUniqueNameException;
import nl.infosupport.intern.recognition.web.models.Person.SavedPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EntryServiceImpl implements EntryService {
    private static Logger logger = LoggerFactory.getLogger(EntryServiceImpl.class);

    //Could be any Azure, Google
    private RecognitionStrategy strategy;
    private PersonRepositoryAdapter repo;

    public EntryServiceImpl(@Qualifier("getAzureRecognitionStrategy") RecognitionStrategy strategy, PersonRepositoryAdapter repo) {
        this.strategy = strategy;
        this.repo = repo;
    }

    @Override
    public SavedPerson register(String name) {
        logger.debug("{}", Thread.currentThread().getName());

        var uniqueName = repo.isUniqueName(name)
                .orElseThrow(() -> new NoUniqueNameException(name));

        var personId = CompletableFuture
                .supplyAsync(() ->
                        strategy.create(uniqueName));

        String savedPersonId = repo.create(name, personId, strategy.getName());

        return new SavedPerson(name, savedPersonId, strategy.getName());
    }


}
