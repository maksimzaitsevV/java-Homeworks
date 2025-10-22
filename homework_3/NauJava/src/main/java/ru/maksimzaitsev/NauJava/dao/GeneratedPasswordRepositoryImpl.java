package ru.maksimzaitsev.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import ru.maksimzaitsev.NauJava.dbEntities.GeneratedPassword;

import java.util.List;

public class GeneratedPasswordRepositoryImpl implements GeneratedPasswordRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public GeneratedPasswordRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GeneratedPassword> findByPasswordLengthAndStrengthRating(Integer passwordLength, String strengthRating) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GeneratedPassword> criteriaQuery = criteriaBuilder.createQuery(GeneratedPassword.class);
        Root<GeneratedPassword> userRoot = criteriaQuery.from(GeneratedPassword.class);
        Predicate lengthPredicate = criteriaBuilder.equal(userRoot.get("passwordLength"), passwordLength);
        Predicate strengthPredicate = criteriaBuilder.equal(userRoot.get("strengthRating"), strengthRating);
        Predicate finalPredicate = criteriaBuilder.and(lengthPredicate, strengthPredicate);
        criteriaQuery.select(userRoot).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }
}
