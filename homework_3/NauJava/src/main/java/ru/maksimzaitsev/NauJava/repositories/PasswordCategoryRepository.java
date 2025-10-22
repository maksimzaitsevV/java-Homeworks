package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.maksimzaitsev.NauJava.dbEntities.PasswordCategories;

public interface PasswordCategoryRepository extends CrudRepository<PasswordCategories, Long> {
}
