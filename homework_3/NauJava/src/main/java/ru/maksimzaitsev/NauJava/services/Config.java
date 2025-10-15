package ru.maksimzaitsev.NauJava.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.maksimzaitsev.NauJava.entity.Password;

@Configuration
public class Config {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.version}")
    private String appVersion;

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Password> passwordContainer() {
        System.out.println(appName);
        System.out.println(appVersion);
        return new ArrayList<>();
    }
}