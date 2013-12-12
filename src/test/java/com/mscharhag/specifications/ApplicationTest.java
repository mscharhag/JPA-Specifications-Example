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
import com.mscharhag.specifications.specs.IsActive;
import com.mscharhag.specifications.specs.IsPopular;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class ApplicationTest {

	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private TestRepository myRepository;
	
	
	@Test
	public void testIsActive() {
		Poll p = new Poll(new DateTime().plusDays(3), new DateTime().plusDays(7));
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		pollRepository.save(p);
		//List<Poll> polls = myRepository.getActivePollsCriteria();
		List<Poll> polls = myRepository.listFromSpecification(new IsPopular().and(new IsActive()));
		
		System.out.println("polls: " + polls);
		//assertTrue(p.isActive());
	}
	
	
	@Test
	public void testIsPopular() {
		Poll p = new Poll(new DateTime(), new DateTime().plusDays(7));
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		p.getVotes().add(new Vote());
		assertTrue(p.isPopular());
	}
}
