package com.mscharhag.specifications.specs;

import java.lang.reflect.ParameterizedType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

abstract public class AbstractSpecification<T> implements Specification<T> {

	@Override
	public boolean isSatisfiedBy(T t) {
		throw new NotImplementedException();
	}
	
	
	@Override
	public Predicate toPredicate(Root<T> poll, CriteriaBuilder cb) {
		throw new NotImplementedException();
	}

	
	@Override
	public Specification<T> and(Specification<T> other) {
		return new AndSpecification<>(this, other);
	}
	
	
	@Override
	public Class<T> getType() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class<T>)type.getActualTypeArguments()[0];
		return clazz;
	}
}
