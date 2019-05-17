package nl.infosupport.intern.recognition.domainservices.repositories;

import nl.infosupport.intern.recognition.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("PersonRepository")
public interface PersonRepository extends CrudRepository<Person, Long>{
    boolean existsByName(String name);
    Optional<Person> findByAzureId(String azureId);
}
