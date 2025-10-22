package ru.maksimzaitsev.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptionAlgorithm;
import ru.maksimzaitsev.NauJava.dbEntities.GeneratedPassword;
import ru.maksimzaitsev.NauJava.repositories.EncryptedPasswordRepository;
import ru.maksimzaitsev.NauJava.repositories.EncryptionAlgorithmRepository;
import ru.maksimzaitsev.NauJava.repositories.GeneratedPasswordRepository;

import java.util.List;

@SpringBootTest
class PasswordRepositoryTest {

    private final GeneratedPasswordRepository generatedPasswordRepository;
    private final EncryptedPasswordRepository encryptedPasswordRepository;
    private final EncryptionAlgorithmRepository encryptionAlgorithmRepository;

    @Autowired
    PasswordRepositoryTest(GeneratedPasswordRepository generatedPasswordRepository,
                           EncryptedPasswordRepository encryptedPasswordRepository,
                           EncryptionAlgorithmRepository encryptionAlgorithmRepository) {
        this.generatedPasswordRepository = generatedPasswordRepository;
        this.encryptedPasswordRepository = encryptedPasswordRepository;
        this.encryptionAlgorithmRepository = encryptionAlgorithmRepository;
    }

    @BeforeEach
    void setUp() {
        encryptedPasswordRepository.deleteAll();
        generatedPasswordRepository.deleteAll();
    }
    @Test
    void testSpringDataMethods() {
        generatedPasswordRepository.deleteAll();
        GeneratedPassword password = new GeneratedPassword();
        password.setPasswordContent("test");
        password.setPasswordLength(8);
        password.setStrengthRating("weak");
        generatedPasswordRepository.save(password);
        List<GeneratedPassword> found = generatedPasswordRepository.findByPasswordLengthAndStrengthRating(8, "weak");

        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals("test", found.getFirst().getPasswordContent());
    }

    @Test
    void testCriteriaApiMethods() {
        EncryptionAlgorithm sha256 = new EncryptionAlgorithm();
        sha256.setName("SHA-256");
        sha256.setKeyLength(256);
        sha256.setDescription("SHA-256");
        EncryptionAlgorithm savedAlgorithm = encryptionAlgorithmRepository.save(sha256);
        GeneratedPassword generated = new GeneratedPassword();
        generated.setPasswordContent("test");
        generated.setPasswordLength(6);
        generated.setStrengthRating("weak");
        GeneratedPassword savedGenerated = generatedPasswordRepository.save(generated);
        EncryptedPassword encrypted = new EncryptedPassword();
        encrypted.setEncryptedContent("encrypted_SHA256");
        encrypted.setIterations(1);
        encrypted.setHashType("SHA-256");
        encrypted.setEncryptionAlgorithm(savedAlgorithm);
        encrypted.setGeneratedPassword(savedGenerated);
        encryptedPasswordRepository.save(encrypted);
        List<EncryptedPassword> found = encryptedPasswordRepository.findByEncryptionAlgorithmName("SHA-256");

        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals("encrypted_SHA256", found.getFirst().getEncryptedContent());
        Assertions.assertEquals("SHA-256", found.getFirst().getEncryptionAlgorithm().getName());
    }
}