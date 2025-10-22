package ru.maksimzaitsev.NauJava.dbEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "generated_passwords")
public class GeneratedPassword {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private GenerationAlgorithm generationAlgorithm;

    @Column
    private String passwordContent;

    @Column
    private Integer passwordLength;

    @Column
    private String strengthRating;

    public String getStrengthRating() {
        return strengthRating;
    }

    public void setStrengthRating(String strengthRating) {
        this.strengthRating = strengthRating;
    }

    public Integer getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(Integer passwordLength) {
        this.passwordLength = passwordLength;
    }

    public String getPasswordContent() {
        return passwordContent;
    }

    public void setPasswordContent(String passwordContent) {
        this.passwordContent = passwordContent;
    }

    public GenerationAlgorithm getGenerationAlgorithm() {
        return generationAlgorithm;
    }

    public void setGenerationAlgorithm(GenerationAlgorithm generationAlgorithm) {
        this.generationAlgorithm = generationAlgorithm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
