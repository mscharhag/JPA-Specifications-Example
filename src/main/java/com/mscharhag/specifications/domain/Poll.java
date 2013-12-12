package com.mscharhag.specifications.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class Poll {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startDate; 
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime endDate;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lockDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Vote> votes = new ArrayList<>();
	
	@Deprecated // required by Hibernate
	public Poll() { }
	
	public Poll(DateTime start, DateTime end) {
		this.startDate = start;
		this.endDate = end;
	}
	
	public boolean isActive() {
		return startDate.isBeforeNow() && endDate.isAfterNow() && lockDate == null;
	}
	
	public boolean isPopular() {
		return votes.size() > 5 && lockDate == null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime start) {
		this.startDate = start;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime end) {
		this.endDate = end;
	}

	public DateTime getLockDate() {
		return lockDate;
	}

	public void setLockDate(DateTime locked) {
		this.lockDate = locked;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
}