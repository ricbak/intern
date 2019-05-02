package nl.infosupport.intern.recognition.domainservices.repositories;

import nl.infosupport.intern.recognition.domain.Person;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PersonRepositoryAdapter {

    Optional<String> isUniqueName(String name);
    String create(String name, CompletableFuture<String> personId);
    List<Person> findAllPersons();
}
