package com.mscharhag.specifications.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;

import com.mscharhag.specifications.domain.Poll;
import com.mscharhag.specifications.domain.Poll_;

public class IsActive extends AbstractSpecification<Poll> {

	@Override
	public boolean isStatisfiedBy(Poll poll) {
		return poll.getStartDate().isBeforeNow() 
				&& poll.getEndDate().isAfterNow() 
				&& poll.getLockDate()== null;
	}

	@Override
	public Predicate toPredicate(Root<Poll> poll, CriteriaBuilder cb) {
		DateTime now = new DateTime();
		return cb.and(
			cb.lessThan(poll.get(Poll_.startDate), now),
			cb.greaterThan(poll.get(Poll_.endDate), now),
			cb.isNull(poll.get(Poll_.lockDate))
		);
	}

}
