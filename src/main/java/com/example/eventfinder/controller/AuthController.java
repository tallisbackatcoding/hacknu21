package com.example.eventfinder.controller;

import javax.validation.Valid;
import com.example.eventfinder.dto.UserLoginDTO;
import com.example.eventfinder.dto.UserRegisterDTO;
import com.example.eventfinder.exception.CustomException;
import com.example.eventfinder.exception.ErrorCode;
import com.example.eventfinder.model.User;
import com.example.eventfinder.repo.UserRepo;
import com.example.eventfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public User login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws Exception {

        Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        User user = null;
        try {
            user = (User)userService.loadUserByUsername(userLoginDTO.getUsername());
        }catch (UsernameNotFoundException e){
            throw new CustomException(ErrorCode.WRONG_CREDENTIALS_ERROR);
        }

        if (!BCrypt.checkpw(userLoginDTO.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.WRONG_CREDENTIALS_ERROR);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
        user = (User) userService.loadUserByUsername(user.getUsername());

        return user;
    }

    @PostMapping("/register")
    public User registerNewUser(@RequestBody UserRegisterDTO userRegisterDTO) throws Exception {

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setCity(userRegisterDTO.getCity());
        user.setGender(userRegisterDTO.getGender());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPreferenceTags(userRegisterDTO.getPreferenceTags());
        try {
            userService.loadUserByUsername(user.getUsername());
            throw new CustomException(ErrorCode.USER_EXISTS_ERROR);
        }catch (UsernameNotFoundException e) {
            userService.createUser(user);
            return login(new UserLoginDTO(user.getUsername(), userRegisterDTO.getPassword()));
        }
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/logout")
    public User logout(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userService.loadUserByUsername(currentPrincipalName);
        session.invalidate();
        return user;
    }

    @GetMapping("/me")
    public User me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return (User) userService.loadUserByUsername(currentPrincipalName);
    }
}