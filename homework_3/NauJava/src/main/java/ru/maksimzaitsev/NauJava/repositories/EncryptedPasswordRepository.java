package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;

import java.util.List;

public interface EncryptedPasswordRepository extends CrudRepository<EncryptedPassword, Long> {
    List<EncryptedPassword> findAll();
    @Query("SELECT encryptedPassword FROM EncryptedPassword encryptedPassword WHERE encryptedPassword.encryptionAlgorithm.name = :algorithmName")
    List<EncryptedPassword> findByEncryptionAlgorithmName(String algorithmName);
}
