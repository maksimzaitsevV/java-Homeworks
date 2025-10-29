package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.maksimzaitsev.NauJava.dbEntities.GenerationAlgorithm;

@RepositoryRestResource(path = "generation-algorithms")
public interface GenerationAlgorithmRepository extends CrudRepository<GenerationAlgorithm, Long> {
}
