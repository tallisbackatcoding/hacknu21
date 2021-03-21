package com.example.eventfinder.service;

import com.example.eventfinder.model.MessageGroup;
import com.example.eventfinder.repo.MessageGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageGroupService {
    @Autowired
    private MessageGroupRepo messageGroupRepo;

    public MessageGroup findById(Integer id){
        return messageGroupRepo.findById(id).orElse(null);
    }

}
