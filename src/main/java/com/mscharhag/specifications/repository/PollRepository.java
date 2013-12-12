package com.mscharhag.specifications.repository;

import org.springframework.data.repository.CrudRepository;

import com.mscharhag.specifications.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
