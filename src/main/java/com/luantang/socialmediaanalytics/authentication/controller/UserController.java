package com.luantang.socialmediaanalytics.authentication.controller;

import com.luantang.socialmediaanalytics.authentication.dto.UserDto;
import com.luantang.socialmediaanalytics.authentication.model.UserEntity;
import com.luantang.socialmediaanalytics.authentication.repository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getUserDtos(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getContextUserDetail() {
        UserEntity contextUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(userService.getUserDto(contextUser), HttpStatus.OK);
    }
}
