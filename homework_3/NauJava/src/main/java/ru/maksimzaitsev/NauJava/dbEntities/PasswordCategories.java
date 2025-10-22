package ru.maksimzaitsev.NauJava.dbEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "password_categories")
public class PasswordCategories
{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String usageHint;

    public String getUsageHint() {
        return usageHint;
    }

    public void setUsageHint(String usageHint) {
        this.usageHint = usageHint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}