package nl.infosupport.intern.recognition.domainservices.repositories;

import nl.infosupport.intern.recognition.domain.Person;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class PersonRepositoryDecorator implements PersonRepository {

    private final PersonRepository repo;

    public PersonRepositoryDecorator(@Qualifier("PersonRepository")PersonRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean existsByName(String name) {
        return repo.existsByName(name);
    }

    @Override
    public Optional<Person> findByAzureId(String azureId) {
        return repo.findByAzureId(azureId);
    }

    @Override
    public <S extends Person> S save(S entity) {
        return repo.save(entity);
    }

    @Override
    public <S extends Person> Iterable<S> saveAll(Iterable<S> entities) {
        return repo.saveAll(entities);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public Iterable<Person> findAll() {
        return repo.findAll();
    }

    @Override
    public Iterable<Person> findAllById(Iterable<Long> ids) {
        return repo.findAllById(ids);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(Person entity) {
        repo.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Person> entities) {
        repo.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

   public abstract Optional<String> isUniqueName(String name);
   public abstract String create(String name, CompletableFuture<String> personId, String source);
}
