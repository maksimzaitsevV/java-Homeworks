package ru.maksimzaitsev.NauJava.dataAccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.maksimzaitsev.NauJava.entity.Password;

@Component
public class PasswordRepository implements CrudRepository<Password, Long> {
    private final List<Password> passwordContainer;

    @Autowired
    public PasswordRepository(List<Password> passwordContainer) {
        this.passwordContainer = passwordContainer;
    }

    @Override
    public void create(Password password) {
        passwordContainer.add(password);
    }

    @Override
    public Password read(Long id) {
        return passwordContainer.stream()
                .filter(password -> password.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Password updatePassword) {
        for (int i = 0; i < passwordContainer.size(); i++) {
            if (passwordContainer.get(i).getId().equals(updatePassword.getId())) {
                passwordContainer.set(i, updatePassword);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        for (int i = 0; i < passwordContainer.size(); i++) {
            if (passwordContainer.get(i).getId().equals(id)) {
                passwordContainer.remove(i);
                return;
            }
        }
    }
}