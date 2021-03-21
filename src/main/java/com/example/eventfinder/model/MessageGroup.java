package com.example.eventfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="t_message")
public class MessageGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_group_id")
    private Integer message_group_id;

    @JsonIgnore
    @OneToOne
    private Event event;

    @OneToMany(mappedBy = "messageGroup")
    private List<MessageBody> messages;

}
