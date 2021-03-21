package com.example.eventfinder.service;


import com.example.eventfinder.model.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import java.util.Set;

public class EventSpecification {
    public EventSpecification(){

    }

    public static Specification<Event> byTags(Set<String> tags) {
        return null;
    }
}
