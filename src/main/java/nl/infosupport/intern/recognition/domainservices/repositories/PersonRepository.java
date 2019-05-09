package nl.infosupport.intern.recognition.domainservices.repositories;

import nl.infosupport.intern.recognition.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, String>{
    boolean existsByName(String name);
    Optional<Person> findByAzureId(String azureId);
}
