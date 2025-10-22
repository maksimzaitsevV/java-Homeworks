package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptionAlgorithm;

public interface EncryptionAlgorithmRepository extends CrudRepository<EncryptionAlgorithm, Long> {
}
