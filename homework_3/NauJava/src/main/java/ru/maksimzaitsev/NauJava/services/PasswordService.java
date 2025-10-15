package ru.maksimzaitsev.NauJava.services;

import ru.maksimzaitsev.NauJava.entity.Password;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface PasswordService
{
    Password createPasswordAfterAlgorithm(Integer length, String algorithmContents);
    Password createEncryptedPassword(Password password) throws NoSuchAlgorithmException;
    Password findById(Long id);
    void deleteById(Long id);
    List<Password> historyEncryptedPassword();
}
