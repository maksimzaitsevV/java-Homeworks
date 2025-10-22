package ru.maksimzaitsev.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;

import java.util.List;

public class EncryptedPasswordRepositoryImpl implements EncryptedPasswordRepositoryCustom {
    private final EntityManager entityManager;

    public EncryptedPasswordRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<EncryptedPassword> findByEncryptionAlgorithmName(String algorithmName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EncryptedPassword> criteriaQuery = criteriaBuilder.createQuery(EncryptedPassword.class);
        Root<EncryptedPassword> userRoot = criteriaQuery.from(EncryptedPassword.class);
        Predicate namePredicate = criteriaBuilder.equal(userRoot.get("encryptionAlgorithm").get("name"), algorithmName);
        criteriaQuery.select(userRoot).where(namePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
