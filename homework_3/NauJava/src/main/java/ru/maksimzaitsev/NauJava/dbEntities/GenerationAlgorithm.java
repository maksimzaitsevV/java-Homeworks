package ru.maksimzaitsev.NauJava.dbEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "generation_algorithms")
public class GenerationAlgorithm {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String characterSet;

    @Column
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
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

