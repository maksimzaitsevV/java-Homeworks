package ru.maksimzaitsev.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maksimzaitsev.NauJava.entity.Password;
import ru.maksimzaitsev.NauJava.dataAccess.PasswordRepository;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordRepository passwordRepository;
    private Long count = 1L;
    private final List<Password> history = new ArrayList<>();

    @Autowired
    public PasswordServiceImpl(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public Password createPasswordAfterAlgorithm(Integer length, String algorithmContents) {
        String passwordAfterAlgorithm = getPassword(length, algorithmContents);
        Password password = new Password();
        password.setId(count);
        count++;
        password.setAlgorithm(algorithmContents);
        password.setPasswordAfterAlgorithm(passwordAfterAlgorithm);
        password.setPasswordLength(length);

        return password;
    }

    private String getPassword(Integer length, String algorithmContents) {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz!@#$%^&*";
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        String chars;

        if (algorithmContents != null) {
            chars = switch (algorithmContents) {
                case "lower" -> lowerChars;
                case "upper" -> upperChars;
                default -> allChars;
            };
        } else {
            chars = allChars;
        }

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }

        return sb.toString();
    }

    @Override
    public Password createEncryptedPassword(Password password) {
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getPasswordAfterAlgorithm().getBytes());
            String hashPassword = Base64.getEncoder().encodeToString(hash);
            password.setEncryptedPassword(hashPassword);
            passwordRepository.create(password);
            history.add(password);

            return password;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Password findById(Long id) {
        return passwordRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        passwordRepository.delete(id);
        history.removeIf(password -> password.getId().equals(id));
    }

    @Override
    public List<Password> historyEncryptedPassword() {
        return new ArrayList<>(history);
    }
}
