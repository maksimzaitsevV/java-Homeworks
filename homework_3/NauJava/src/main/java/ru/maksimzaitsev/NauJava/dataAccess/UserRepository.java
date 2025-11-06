package ru.maksimzaitsev.NauJava.dataAccess;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.maksimzaitsev.NauJava.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}