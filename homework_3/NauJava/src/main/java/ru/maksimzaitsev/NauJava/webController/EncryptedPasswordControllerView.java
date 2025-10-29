package ru.maksimzaitsev.NauJava.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maksimzaitsev.NauJava.repositories.EncryptedPasswordRepository;

@Controller
@RequestMapping("/custom/encryptedPasswords/view")
public class EncryptedPasswordControllerView {
    @Autowired
    private EncryptedPasswordRepository encryptedPasswordRepository;

    @GetMapping("/list")
    public String encryptedPasswordsListView(Model model) {
        model.addAttribute("encryptedPasswords", encryptedPasswordRepository.findAll());
        return "passwords";
    }
}
