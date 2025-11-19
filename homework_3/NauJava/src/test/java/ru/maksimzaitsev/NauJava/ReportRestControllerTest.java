package ru.maksimzaitsev.NauJava;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.maksimzaitsev.NauJava.dataAccess.UserRepository;
import ru.maksimzaitsev.NauJava.entity.User;

import java.util.Map;

@SpringBootTest()
public class ReportRestControllerTest {

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
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        userRepository.deleteAll();
        createUser();
    }

    @Test
    public void testCreateReport() {
        var response = RestAssured.given()
                .auth()
                .basic("user1", "user")
                .when()
                .get("/api/reports/create")
                .then()
                .statusCode(200)
                .extract()
                .as(Map.class);

        Assertions.assertNotNull(response.get("reportId"));
        Assertions.assertEquals("CREATED", response.get("status"));
        Assertions.assertEquals("Отчет создан", response.get("message"));
    }

    @Test
    public void testGetReportNotFound() {
        var response = RestAssured.given()
                .auth()
                .basic("user1", "user")
                .when()
                .get("/api/reports/99999999")
                .then()
                .statusCode(400)
                .extract()
                .as(Map.class);

        Assertions.assertEquals("Отчет не найден", response.get("error"));
        Assertions.assertTrue(response.get("message").toString().contains("не найден"));
    }

    @Test
    public void testGetReportFound() {
        var createResponse = RestAssured.given()
                .auth()
                .basic("user1", "user")
                .when()
                .get("/api/reports/create")
                .then()
                .statusCode(200)
                .extract()
                .as(Map.class);

        var reportId = createResponse.get("reportId");

        var getResponse = RestAssured.given()
                .accept("application/json")
                .auth()
                .basic("user1", "user")
                .when()
                .get("/api/reports/" + reportId)
                .then()
                .statusCode(200)
                .extract()
                .as(Map.class);

        Assertions.assertEquals(reportId, getResponse.get("reportId"));
        Assertions.assertNotNull(getResponse.get("status"));
        Assertions.assertNotNull(getResponse.get("message"));
    }
}