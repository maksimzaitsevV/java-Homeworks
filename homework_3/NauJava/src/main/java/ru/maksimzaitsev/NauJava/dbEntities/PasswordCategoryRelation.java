package ru.maksimzaitsev.NauJava.dbEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "password_category_relations")
public class PasswordCategoryRelation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private GeneratedPassword generatedPassword;

    @ManyToOne
    private PasswordCategories passwordCategories;

    public PasswordCategories getPasswordCategories() {
        return passwordCategories;
    }

    public void setPasswordCategories(PasswordCategories passwordCategories) {
        this.passwordCategories = passwordCategories;
    }

    public GeneratedPassword getGeneratedPassword() {
        return generatedPassword;
    }

    public void setGeneratedPassword(GeneratedPassword generatedPassword) {
        this.generatedPassword = generatedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}