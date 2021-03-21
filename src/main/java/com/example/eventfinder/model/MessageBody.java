package com.example.eventfinder.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="message_body")
public class MessageBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_body_id")
    private Integer id;

    private Date sentDate;
    @JsonIgnore
    @ManyToOne
    private MessageGroup messageGroup;

    @JsonIgnore
    @ManyToOne
    private User user;

    private String username;

    private String text;

}
