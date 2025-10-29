package ru.maksimzaitsev.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.maksimzaitsev.NauJava.dbEntities.GeneratedPassword;
import ru.maksimzaitsev.NauJava.repositories.GeneratedPasswordRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/passwords")
public class GeneratedPasswordController {
    @Autowired
    private GeneratedPasswordRepository generatedPasswordRepository;

    @GetMapping("/generated/byPasswordLengthAndStrengthRating")
    public List<GeneratedPassword> findByPasswordLengthAndStrengthRating(@RequestParam Integer passwordLength,
                                                                         @RequestParam String strengthRating) {
        return generatedPasswordRepository.findByPasswordLengthAndStrengthRating(passwordLength, strengthRating);
    }
}
