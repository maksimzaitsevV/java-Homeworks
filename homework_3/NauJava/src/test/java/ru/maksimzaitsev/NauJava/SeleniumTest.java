package ru.maksimzaitsev.NauJava;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.maksimzaitsev.NauJava.dataAccess.UserRepository;
import ru.maksimzaitsev.NauJava.entity.User;

import java.time.Duration;
import java.util.Objects;

@SpringBootTest
public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private void createUser() {
        var pass = passwordEncoder.encode("user");
        var user = new User();
        user.setUsername("user1");
        user.setPassword(pass);
        userRepository.save(user);
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        userRepository.deleteAll();
        createUser();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testSuccessfulLogin() {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("user1");
        driver.findElement(By.name("password")).sendKeys("user");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlToBe("http://localhost:8080/")
        ));
    }

    @Test
    void testLogout() {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("user1");
        driver.findElement(By.name("password")).sendKeys("user");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));

        driver.get("http://localhost:8080/logout");

        var logoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.className("primary"))
        );
        logoutButton.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/login?logout"));
    }
}