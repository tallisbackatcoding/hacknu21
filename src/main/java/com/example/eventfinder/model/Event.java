package com.example.eventfinder.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_event")
public class Event {
    public static final String ATTRIBUTE_TAGS = "tags";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer id;

    @OneToOne
    private MessageGroup messageGroup;

    private String city;

    @ElementCollection
    @CollectionTable(
            name = "tags",
            joinColumns = @JoinColumn(name = "event_id")
    )
    private Set<String> tags;

    private String name;

    private String description;

    private Date startDate;

    private Date postedDate;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> attendingPeople;

    private Integer maxPeople;

    @ManyToOne
    private User ownerUser;
}
