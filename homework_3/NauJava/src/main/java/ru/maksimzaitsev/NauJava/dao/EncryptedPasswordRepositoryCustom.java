    package ru.maksimzaitsev.NauJava.dao;

    import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;

    import java.util.List;

    public interface EncryptedPasswordRepositoryCustom {
        List<EncryptedPassword> findByEncryptionAlgorithmName(String algorithmName);
    }
