package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.maksimzaitsev.NauJava.dbEntities.PasswordCategoryRelation;

public interface PasswordCategoryRelationRepository extends CrudRepository<PasswordCategoryRelation, Long> {
}
