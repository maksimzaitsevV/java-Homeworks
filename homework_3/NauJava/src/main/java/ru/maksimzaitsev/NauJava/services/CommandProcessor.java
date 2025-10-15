package ru.maksimzaitsev.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.maksimzaitsev.NauJava.entity.Password;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
public class CommandProcessor {
    private final PasswordService passwordService;

    @Autowired
    public CommandProcessor(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    public void processCommand(String input) throws NoSuchAlgorithmException {
        String[] cmd = input.split(" ");
        switch (cmd[0]) {
            case "create" -> {
                if (Integer.parseInt(cmd[1]) < 1) {
                    System.out.println("Длина должна быть положительной величиной");
                    return;
                }

                if (cmd.length == 2) {
                    System.out.println("Команда create" + cmd[1] + "all");
                    return;
                }
                Password password = passwordService.createPasswordAfterAlgorithm(Integer.valueOf(cmd[1]), cmd[2]);
                Password encryptedPassword = passwordService.createEncryptedPassword(password);
                System.out.println("Пароль успешно создан и зашифрован... " + password.getPasswordAfterAlgorithm() + "id: " + password.getId());
            }

            case "find" -> {
                Password password = passwordService.findById(Long.valueOf(cmd[1]));
                System.out.println();
                if (password != null)
                    System.out.println("Пароль: " + password.getPasswordAfterAlgorithm());
                else
                    System.out.println("Пароль не найден");
            }

            case "delete" -> {
                passwordService.deleteById(Long.valueOf(cmd[1]));
                System.out.println("Пароль удалён");
            }

            case "history" -> {
                List<Password> history = passwordService.historyEncryptedPassword();
                if (!history.isEmpty()) {
                    System.out.println("История зашифрованных паролей");
                    for (Password password : history) {
                        System.out.println(password.getEncryptedPassword());
                    }
                }
            }
            default -> System.out.println("Введена неизвестная команда...");
        }
    }
}