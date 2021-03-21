package com.example.eventfinder.service;

import com.example.eventfinder.dto.EventCreateDTO;
import com.example.eventfinder.dto.EventForUserDTO;
import com.example.eventfinder.model.Event;
import com.example.eventfinder.model.MessageGroup;
import com.example.eventfinder.model.User;
import com.example.eventfinder.repo.EventRepository;
import com.example.eventfinder.repo.MessageGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MessageGroupRepo messageGroupRepo;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserService userService;

    public Event saveEvent(EventCreateDTO eventCreateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userService.loadUserByUsername(currentPrincipalName);
        Event event = new Event();
        event.setOwnerUser(user);
        event.setDescription(eventCreateDTO.getDescription());
        event.setCity(user.getCity());
        event.setName(eventCreateDTO.getName());
        event.setPostedDate(new Date());
        event.setTags(eventCreateDTO.getTags());
        event.setStartDate(eventCreateDTO.getStartDate());
        event.setMaxPeople(eventCreateDTO.getMaxPeople());
        eventRepository.save(event);
        MessageGroup messageGroup = new MessageGroup();
        messageGroup.setEvent(event);
        messageGroupRepo.save(messageGroup);
        event.setMessageGroup(messageGroup);
        eventRepository.save(event);
        return event;
    }

    public Page<EventForUserDTO> getAll(Set<String> chosenTags, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userService.loadUserByUsername(currentPrincipalName);
        List<Event> events = eventRepository.findAllByCity(user.getCity());
        Set<String> tags = new HashSet<>();

        if(chosenTags != null){
            tags.addAll(chosenTags);
        }

        if(!tags.isEmpty()){
            events = events.stream().filter(event -> {
                for (String tag : tags) {
                    if(event.getTags().contains(tag)){
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
        }

        List<EventForUserDTO> eventsForUser = events.stream().map(event -> {
            EventForUserDTO eventForUserDTO = new EventForUserDTO();
            eventForUserDTO.setEvent_id(event.getId());
            eventForUserDTO.setOwnerUser(event.getOwnerUser());
            eventForUserDTO.setName(event.getName());
            eventForUserDTO.setCity(event.getCity());
            eventForUserDTO.setPostedDate(event.getPostedDate().toString().substring(0, 10));
            eventForUserDTO.setMaxPeople(event.getMaxPeople());
            eventForUserDTO.setGoingPeopleSize(event.getAttendingPeople().size());
            eventForUserDTO.setDescription(event.getDescription());
            eventForUserDTO.setTags(event.getTags());
            eventForUserDTO.setStartDate(event.getStartDate().toString().substring(0, 10));
            boolean going = false;
            if(event.getAttendingPeople().contains(user)){
                going = true;
            }
            eventForUserDTO.setGoing(going);
            return eventForUserDTO;
        }).sorted(Comparator.comparing(EventForUserDTO::getStartDate)).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), eventsForUser.size());
        Page<EventForUserDTO> page;
        page = new PageImpl<>(eventsForUser.subList(start, end), pageable, events.size());
        return page;
    }

}
