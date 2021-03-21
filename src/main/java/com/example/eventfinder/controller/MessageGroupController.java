package com.example.eventfinder.controller;

import com.example.eventfinder.dto.MessageBodyDTO;
import com.example.eventfinder.model.Event;
import com.example.eventfinder.model.MessageBody;
import com.example.eventfinder.model.MessageGroup;
import com.example.eventfinder.model.User;
import com.example.eventfinder.repo.EventRepository;
import com.example.eventfinder.repo.MessageBodyRepository;
import com.example.eventfinder.repo.MessageGroupRepo;
import com.example.eventfinder.service.MessageGroupService;
import com.example.eventfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/message")
public class MessageGroupController {

    @Autowired
    private UserService userService;
    @Autowired
    public MessageGroupService messageGroupService;
    @Autowired
    private MessageGroupRepo messageGroupRepo;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MessageBodyRepository messageBodyRepository;

    @GetMapping("/{id}")
    public MessageGroup getMessageGroupByEventId(@PathVariable Integer id){
        return messageGroupService.findById(id);
    }
    @PostMapping("/{id}")
    public MessageBody sendMessage(@RequestBody MessageBodyDTO messageBodyDTO, @PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userService.loadUserByUsername(currentPrincipalName);

        MessageBody messageBody = new MessageBody();
        messageBody.setMessageGroup(messageGroupRepo.findById(eventRepository.findById(id).orElseThrow(RuntimeException::new).getMessageGroup().getMessage_group_id()).orElseThrow(RuntimeException::new));
        messageBody.setText(messageBodyDTO.getText());
        messageBody.setUsername(user.getUsername());
        messageBody.setSentDate(new Date());
        messageBodyRepository.save(messageBody);
        return messageBody;
    }
}
