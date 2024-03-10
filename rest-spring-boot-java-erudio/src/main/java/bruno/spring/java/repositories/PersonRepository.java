package bruno.spring.java.repositories;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bruno.spring.java.models.Person;

@ComponentScan
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}
