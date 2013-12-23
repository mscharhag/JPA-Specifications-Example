package com.mscharhag.specifications.repository;

import com.mscharhag.specifications.domain.Poll;
import com.mscharhag.specifications.specification.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class PollRepository {

    @PersistenceContext
    private EntityManager entityManager;


	@Transactional
    public void save(Poll poll) {
        this.entityManager.persist(poll);
		this.entityManager.flush();
    }

    public <T> List<T> findAllBySpecification(Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(specification.getType());
        Root<T> root = criteriaQuery.from(specification.getType());

        Predicate predicate = specification.toPredicate(root, criteriaBuilder);

        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
