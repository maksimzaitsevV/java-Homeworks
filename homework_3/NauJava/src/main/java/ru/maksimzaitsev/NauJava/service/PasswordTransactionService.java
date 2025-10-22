package ru.maksimzaitsev.NauJava.service;

public interface PasswordTransactionService {
    void deleteGeneratedPasswordAndEncrypted(Long generatedPasswordId);
}
