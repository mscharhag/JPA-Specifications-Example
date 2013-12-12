package com.mscharhag.specifications.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.mscharhag.specifications.domain.Poll;
import com.mscharhag.specifications.domain.Poll_;
import com.mscharhag.specifications.specs.Specification;

@Component
public class TestRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Poll> getActivePolls() {
		Query query = entityManager.createQuery("FROM Poll where startDate < :now and endDate > :now and lockDate is null", Poll.class);
		query.setParameter("now", new DateTime());
		return query.getResultList();
	}
	
	
	public <T> List<T> listFromSpecification(Specification<T> specification) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		ParameterizedType type = (ParameterizedType) specification.getClass().getGenericSuperclass();
//		Class<T> clazz = (Class<T>)type.getActualTypeArguments()[0];
		
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(specification.getType());
		Root<T> root = criteriaQuery.from(specification.getType());
		Predicate predicate = specification.toPredicate(root, criteriaBuilder);
		criteriaQuery.where(predicate);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	
	public List<Poll> getActivePollsCriteria() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Poll> criteriaQuery = criteriaBuilder.createQuery(Poll.class);
		Root<Poll> poll = criteriaQuery.from(Poll.class);
		DateTime now = new DateTime();
		Predicate startDate = criteriaBuilder.lessThan(poll.get(Poll_.startDate), now);
		Predicate endDate = criteriaBuilder.greaterThan(poll.get(Poll_.endDate), now);
		Predicate locked = criteriaBuilder.isNull(poll.get(Poll_.lockDate));
		Predicate p = criteriaBuilder.and(startDate, endDate, locked);
		criteriaQuery.where(p);
		TypedQuery<Poll> q = entityManager.createQuery(criteriaQuery);
		return q.getResultList();
				
//				CriteriaBuilder qb = em.getCriteriaBuilder();
//				CriteriaQuery<Person> c = qb.createQuery(Person.class);
//				Root<Person> p = c.from(Person.class);
//				Predicate condition = qb.gt(p.get(Person_.age), 20);
//				c.where(condition);
//				TypedQuery<Person> q = em.createQuery(c); 
//				List<Person> result = q.getResultList();
//				
//		criteriaQuery.s
//		criteriaQuery.select(person).where(criteriaBuilder.equal(
//		 person.get(Person_.firstName), "Peter")).distinct(true);
//
//		person.join(Person_.addresses);
//
//		List<Person> resultList = entityManager.createQuery(criteriaQuery).getResultList();
	}

}
