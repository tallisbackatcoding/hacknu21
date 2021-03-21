package com.example.eventfinder.service;

import com.example.eventfinder.model.Role;
import com.example.eventfinder.model.User;
import com.example.eventfinder.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Incorrect username or password");
        }else{
            return user;
        }
    }

    public User loadById(Integer userId) throws UsernameNotFoundException {
        User user = userRepo.findById(userId).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("Incorrect username or password");
        }else{
            return user;
        }
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    public boolean createUser(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role(1L, "ROLE_USER"));
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }
}