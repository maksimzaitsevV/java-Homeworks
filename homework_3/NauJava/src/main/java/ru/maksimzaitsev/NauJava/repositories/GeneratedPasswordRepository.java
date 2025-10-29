package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.maksimzaitsev.NauJava.dbEntities.GeneratedPassword;

import java.util.List;

@RepositoryRestResource(path = "generated-passwords")
public interface GeneratedPasswordRepository extends CrudRepository<GeneratedPassword, Long> {
    List<GeneratedPassword> findByPasswordLengthAndStrengthRating(Integer passwordLength, String strengthRating);
}
