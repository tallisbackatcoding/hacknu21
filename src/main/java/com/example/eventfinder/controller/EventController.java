package com.example.eventfinder.controller;

import com.example.eventfinder.dto.EventCreateDTO;
import com.example.eventfinder.dto.EventForUserDTO;
import com.example.eventfinder.model.Event;
import com.example.eventfinder.repo.EventRepository;
import com.example.eventfinder.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/all")
    public Page<EventForUserDTO> getEvents(Pageable pageable, @RequestParam(value = "tags", required = false) Set<String> tags){
        return eventService.getAll(tags, pageable);
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable Integer id){
        return eventRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    @PostMapping
    public Event saveEvent(@RequestBody EventCreateDTO eventCreateDTO){
        return eventService.saveEvent(eventCreateDTO);
    }
}
