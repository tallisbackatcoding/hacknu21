package com.example.eventfinder.repo;

import com.example.eventfinder.model.MessageBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface MessageBodyRepository extends JpaRepository<MessageBody, Integer> {
}
