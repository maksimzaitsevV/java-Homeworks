package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptionAlgorithm;

@RepositoryRestResource(path = "encryption-algorithms")
public interface EncryptionAlgorithmRepository extends CrudRepository<EncryptionAlgorithm, Long> {
}
