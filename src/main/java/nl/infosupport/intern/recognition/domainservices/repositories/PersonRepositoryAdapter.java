package nl.infosupport.intern.recognition.domainservices.repositories;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PersonRepositoryAdapter {

    Optional<String> isUniqueName(String name);
    String create(String name, CompletableFuture<String> personId, String source);
}
