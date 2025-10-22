package ru.maksimzaitsev.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptionAlgorithm;
import ru.maksimzaitsev.NauJava.dbEntities.GeneratedPassword;
import ru.maksimzaitsev.NauJava.repositories.EncryptedPasswordRepository;
import ru.maksimzaitsev.NauJava.repositories.EncryptionAlgorithmRepository;
import ru.maksimzaitsev.NauJava.repositories.GeneratedPasswordRepository;
import ru.maksimzaitsev.NauJava.service.PasswordTransactionService;

import java.util.Optional;

@SpringBootTest
class PasswordTransactionTest {

    private final PasswordTransactionService passwordTransactionService;
    private final GeneratedPasswordRepository generatedPasswordRepository;
    private final EncryptedPasswordRepository encryptedPasswordRepository;
    private final EncryptionAlgorithmRepository encryptionAlgorithmRepository;

    @Autowired
    public PasswordTransactionTest(PasswordTransactionService passwordTransactionService, GeneratedPasswordRepository generatedPasswordRepository, EncryptedPasswordRepository encryptedPasswordRepository, EncryptionAlgorithmRepository encryptionAlgorithmRepository) {
        this.passwordTransactionService = passwordTransactionService;
        this.generatedPasswordRepository = generatedPasswordRepository;
        this.encryptedPasswordRepository = encryptedPasswordRepository;
        this.encryptionAlgorithmRepository = encryptionAlgorithmRepository;
    }

    @Test
    void testDeleteGeneratedPasswordAndEncrypted() {
        EncryptionAlgorithm sha256 = new EncryptionAlgorithm();
        sha256.setName("SHA-256");
        sha256.setKeyLength(256);
        EncryptionAlgorithm savedAlgorithm = encryptionAlgorithmRepository.save(sha256);
        GeneratedPassword generatedPassword = new GeneratedPassword();
        generatedPassword.setPasswordContent("test");
        generatedPassword.setPasswordLength(10);
        generatedPassword.setStrengthRating("medium");
        GeneratedPassword savedGenerated = generatedPasswordRepository.save(generatedPassword);
        EncryptedPassword encrypted1 = new EncryptedPassword();
        encrypted1.setEncryptedContent("sha256_1");
        encrypted1.setIterations(1);
        encrypted1.setHashType("SHA-256");
        encrypted1.setEncryptionAlgorithm(savedAlgorithm);
        encrypted1.setGeneratedPassword(savedGenerated);
        encryptedPasswordRepository.save(encrypted1);
        EncryptedPassword encrypted2 = new EncryptedPassword();
        encrypted2.setEncryptedContent("sha256_2");
        encrypted2.setIterations(1);
        encrypted2.setHashType("SHA-256");
        encrypted2.setEncryptionAlgorithm(savedAlgorithm);
        encrypted2.setGeneratedPassword(savedGenerated);
        encryptedPasswordRepository.save(encrypted2);
        passwordTransactionService.deleteGeneratedPasswordAndEncrypted(savedGenerated.getId());
        Optional<GeneratedPassword> foundGenerated = generatedPasswordRepository.findById(savedGenerated.getId());
        Assertions.assertTrue(foundGenerated.isEmpty());
        boolean anyEncryptedRemains = encryptedPasswordRepository.findAll().stream().anyMatch(ep -> ep.getGeneratedPassword().getId().equals(savedGenerated.getId()));
        Assertions.assertFalse(anyEncryptedRemains);
    }
}