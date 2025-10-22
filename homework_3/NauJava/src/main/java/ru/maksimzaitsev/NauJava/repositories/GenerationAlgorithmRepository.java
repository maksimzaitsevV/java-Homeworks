package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.maksimzaitsev.NauJava.dbEntities.GenerationAlgorithm;

public interface GenerationAlgorithmRepository extends CrudRepository<GenerationAlgorithm, Long> {
}
