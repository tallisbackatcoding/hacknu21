package com.example.eventfinder.repo;

import com.example.eventfinder.model.MessageGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageGroupRepo extends JpaRepository<MessageGroup, Integer> {
}
