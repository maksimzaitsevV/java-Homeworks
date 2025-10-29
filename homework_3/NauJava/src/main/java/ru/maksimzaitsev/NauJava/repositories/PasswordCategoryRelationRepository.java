package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.maksimzaitsev.NauJava.dbEntities.PasswordCategoryRelation;

@RepositoryRestResource(path = "password-category-relations")
public interface PasswordCategoryRelationRepository extends CrudRepository<PasswordCategoryRelation, Long> {
}
