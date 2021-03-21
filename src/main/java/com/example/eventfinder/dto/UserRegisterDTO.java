package com.example.eventfinder.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserRegisterDTO {
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String gender;
    public String city;
    public Set<String> preferenceTags;
}
