package ru.maksimzaitsev.NauJava.dao;

import ru.maksimzaitsev.NauJava.dbEntities.GeneratedPassword;

import java.util.List;

public interface GeneratedPasswordRepositoryCustom {
    List<GeneratedPassword> findByPasswordLengthAndStrengthRating(Integer passwordLength, String strengthRating);
}
