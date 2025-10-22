package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.maksimzaitsev.NauJava.dbEntities.GeneratedPassword;

import java.util.List;

public interface GeneratedPasswordRepository extends CrudRepository<GeneratedPassword, Long> {
    List<GeneratedPassword> findByPasswordLengthAndStrengthRating(Integer passwordLength, String strengthRating);
}
