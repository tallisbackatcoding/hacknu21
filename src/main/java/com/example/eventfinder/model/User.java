package com.example.eventfinder.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_user")
public class User implements UserDetails {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String city;

    @ElementCollection
    @CollectionTable(
            name = "preferenceTags",
            joinColumns = @JoinColumn(name = "id")
    )
    private Set<String> preferenceTags;

    @JsonIgnore
    @OneToMany(mappedBy = "ownerUser")
    private List<Event> createdEvents;

    @JsonIgnore
    @ManyToMany(mappedBy = "attendingPeople")
    private List<Event> attendingEvents;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<MessageBody> messageBodies;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}