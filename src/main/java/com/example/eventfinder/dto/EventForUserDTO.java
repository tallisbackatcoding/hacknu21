package com.example.eventfinder.dto;

import com.example.eventfinder.model.User;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class EventForUserDTO {
    private Integer event_id;
    private String city;
    private Set<String> tags;
    private String name;
    private String description;
    private String startDate;
    private String postedDate;
    private Integer goingPeopleSize;
    private Integer maxPeople;
    private User ownerUser;
    private boolean isGoing;
}
