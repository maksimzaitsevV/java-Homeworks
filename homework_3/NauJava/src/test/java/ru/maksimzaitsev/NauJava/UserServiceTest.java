package ru.maksimzaitsev.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.maksimzaitsev.NauJava.dataAccess.UserRepository;
import ru.maksimzaitsev.NauJava.entity.User;
import ru.maksimzaitsev.NauJava.services.UserService;

import java.util.Optional;

public class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void testAddUser() {
        var user = new User();
        user.setPassword("123");

        Mockito.when(passwordEncoder.encode("123"))
                .thenReturn("encoded123");
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        var result = userService.addUser(user);

        Assertions.assertEquals("encoded123", user.getPassword());
        Assertions.assertEquals(user, result);
        Mockito.verify(passwordEncoder).encode("123");
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void testAddUserRepositoryThrowsException() {
        var user = new User();
        user.setPassword("pass");

        Mockito.when(passwordEncoder.encode("pass"))
                .thenReturn("encodedPass");
        Mockito.when(userRepository.save(user))
                .thenThrow(new RuntimeException("DB error"));

        var exception = Assertions.assertThrows(RuntimeException.class,
                () -> userService.addUser(user));

        Assertions.assertEquals("DB error", exception.getMessage());
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void testFindUserByUsernameFound() {
        var user = new User();
        user.setUsername("sasha");

        Mockito.when(userRepository.findByUsername("sasha"))
                .thenReturn(Optional.of(user));

        var result = userService.findUserByUsername("sasha");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("sasha", result.getUsername());
        Mockito.verify(userRepository).findByUsername("sasha");
    }

    @Test
    public void testFindUserByUsernameNotFound() {
        Mockito.when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        var result = userService.findUserByUsername("unknown");

        Assertions.assertNull(result);
        Mockito.verify(userRepository).findByUsername("unknown");
    }
}