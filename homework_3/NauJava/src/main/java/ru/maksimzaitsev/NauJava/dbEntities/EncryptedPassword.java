package ru.maksimzaitsev.NauJava.dbEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "encrypted_passwords")
public class EncryptedPassword {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private GeneratedPassword generatedPassword;

    @ManyToOne
    private EncryptionAlgorithm encryptionAlgorithm;

    @Column
    private String encryptedContent;

    @Column
    private Integer iterations;

    @Column
    private String hashType;

    public String getHashType() {
        return hashType;
    }

    public void setHashType(String hashType) {
        this.hashType = hashType;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

    public String getEncryptedContent() {
        return encryptedContent;
    }

    public void setEncryptedContent(String encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
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
