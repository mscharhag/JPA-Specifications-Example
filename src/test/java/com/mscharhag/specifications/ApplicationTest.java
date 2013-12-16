package com.mscharhag.specifications;

import java.util.List;

import org.joda.time.DateTime;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mscharhag.specifications.dao.TestRepository;
import com.mscharhag.specifications.domain.Poll;
import com.mscharhag.specifications.domain.Vote;
import com.mscharhag.specifications.repository.PollRepository;
import com.mscharhag.specifications.specs.AndSpecification;
import com.mscharhag.specifications.specs.IsCurrentlyRunning;
import com.mscharhag.specifications.specs.IsPopular;
import com.mscharhag.specifications.specs.Specification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class ApplicationTest {

	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private TestRepository myRepository;
	
	
	@Test
	public void testIsActive() {
		Poll p = new Poll();
		p.setStartDate(new DateTime());
		p.setEndDate(new DateTime().plusDays(7));
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		pollRepository.save(p);
		//List<Poll> polls = myRepository.getActivePollsCriteria();
//		Specification<Poll> popularAndRunning = new AndSpecification<>(new IsPopular(), new IsCurrentlyRunning());
//		List<Poll> polls = myRepository.findAllBySpecification(popularAndRunning);
		Specification<Poll> popularAndRunning = new IsPopular().and(new IsCurrentlyRunning());
		List<Poll> polls = myRepository.findAllBySpecification(popularAndRunning);
		
		System.out.println("polls: " + polls);
		//assertTrue(p.isActive());
	}
	
	
	@Test
	public void testIsPopular() {
		Poll p = new Poll();
		p.setStartDate(new DateTime());
		p.setEndDate(new DateTime().plusDays(7));
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
	//	new IsCurrentlyRunning().isSatisfiedBy(poll);
		//assertTrue(p.isPopular());
	}
}
