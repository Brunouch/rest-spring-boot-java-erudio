package bruno.spring.java.repositories;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bruno.spring.java.models.Book;

@ComponentScan
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}
