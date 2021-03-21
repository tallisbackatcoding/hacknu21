package com.example.eventfinder.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class EventCreateDTO {
    private String name;
    private String description;
    private Date startDate;
    private Integer maxPeople;
    private Set<String> tags;

}
