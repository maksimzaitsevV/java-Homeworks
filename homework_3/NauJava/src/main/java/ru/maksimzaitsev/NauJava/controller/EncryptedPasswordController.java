package ru.maksimzaitsev.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;
import ru.maksimzaitsev.NauJava.repositories.EncryptedPasswordRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/encryptedPasswords")
public class EncryptedPasswordController {
    @Autowired
    private EncryptedPasswordRepository encryptedPasswordRepository;

    @GetMapping("/findAll")
    public List<EncryptedPassword> findAll() {
        return encryptedPasswordRepository.findAll();
    }

    @GetMapping("/findByEncryptionAlgorithmName")
    public List<EncryptedPassword> findByEncryptionAlgorithmName(@RequestParam("algorithmName") String algorithmName) {
        return encryptedPasswordRepository.findByEncryptionAlgorithmName(algorithmName);
    }
}
