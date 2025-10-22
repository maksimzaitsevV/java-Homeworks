package ru.maksimzaitsev.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;
import ru.maksimzaitsev.NauJava.repositories.EncryptedPasswordRepository;
import ru.maksimzaitsev.NauJava.repositories.GeneratedPasswordRepository;
import java.util.List;

@Service
public class PasswordTransactionServiceImpl implements PasswordTransactionService {

    private final GeneratedPasswordRepository generatedPasswordRepository;
    private final EncryptedPasswordRepository encryptedPasswordRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public PasswordTransactionServiceImpl(GeneratedPasswordRepository generatedPasswordRepository,
                                          EncryptedPasswordRepository encryptedPasswordRepository,
                                          PlatformTransactionManager transactionManager) {
        this.generatedPasswordRepository = generatedPasswordRepository;
        this.encryptedPasswordRepository = encryptedPasswordRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void deleteGeneratedPasswordAndEncrypted(Long generatedPasswordId) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<EncryptedPassword> encryptedPasswords = encryptedPasswordRepository
                    .findAll()
                    .stream()
                    .filter(encryptedPassword -> encryptedPassword
                            .getGeneratedPassword()
                            .getId()
                            .equals(generatedPasswordId))
                    .toList();

            for (EncryptedPassword encryptedPassword : encryptedPasswords) {
                encryptedPasswordRepository.delete(encryptedPassword);
            }
            generatedPasswordRepository.deleteById(generatedPasswordId);
            transactionManager.commit(status);
        } catch (DataAccessException ex) {
            transactionManager.rollback(status);
            throw ex;
        }

    }
}
