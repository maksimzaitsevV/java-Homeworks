package ru.maksimzaitsev.NauJava.entity;

public class Password {
    private Long id;
    private String algorithm;
    private String passwordAfterAlgorithm;
    private String encryptedPassword;
    private Integer passwordLength;

    public Long getId() {
        return id;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPasswordAfterAlgorithm() {
        return passwordAfterAlgorithm;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public Integer getPasswordLength() {
        return  passwordLength;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setPasswordAfterAlgorithm(String passwordAfterAlgorithm) {
        this.passwordAfterAlgorithm = passwordAfterAlgorithm;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setPasswordLength(Integer passwordLength) {
        this.passwordLength = passwordLength;
    }
}
