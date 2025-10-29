package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.maksimzaitsev.NauJava.dbEntities.PasswordCategories;

@RepositoryRestResource(path = "password-categories")
public interface PasswordCategoryRepository extends CrudRepository<PasswordCategories, Long> {
}
