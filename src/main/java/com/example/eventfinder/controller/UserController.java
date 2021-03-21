package com.example.eventfinder.controller;

import com.example.eventfinder.dto.EventForUserDTO;
import com.example.eventfinder.model.Event;
import com.example.eventfinder.model.User;
import com.example.eventfinder.repo.EventRepository;
import com.example.eventfinder.repo.UserRepo;
import com.example.eventfinder.service.EventService;
import com.example.eventfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @PatchMapping("/tags")
    public User updatePreferences(@RequestBody Set<String> tags){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userService.loadUserByUsername(currentPrincipalName);
        Set<String> newPreferences = Stream.concat(user.getPreferenceTags().stream(), tags.stream())
                .collect(Collectors.toSet());
        user.setPreferenceTags(newPreferences);
        userService.updateUser(user);
        return user;
    }

    @PatchMapping("/go/{id}")
    public EventForUserDTO addMyself(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userService.loadUserByUsername(currentPrincipalName);
        Event event = eventRepository.findById(id).orElseThrow(RuntimeException::new);
        boolean isGoing;
        if(event.getAttendingPeople().contains(user)){
            event.getAttendingPeople().remove(user);
            isGoing = false;
        }else{
            event.getAttendingPeople().add(user);
            isGoing = true;
        }
        eventRepository.save(event);
        EventForUserDTO eventForUserDTO = new EventForUserDTO();
        eventForUserDTO.setEvent_id(event.getId());
        eventForUserDTO.setGoing(isGoing);
        return eventForUserDTO;
    }
}
