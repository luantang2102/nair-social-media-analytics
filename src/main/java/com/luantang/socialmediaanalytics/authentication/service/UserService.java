package com.luantang.socialmediaanalytics.authentication.service;

import com.luantang.socialmediaanalytics.authentication.dto.UserDto;
import com.luantang.socialmediaanalytics.authentication.model.UserEntity;

import java.util.List;


public interface UserService {
    UserEntity createUser(UserEntity user);

    List<UserEntity> getUsers();

    List<UserDto> getUserDtos();

    UserDto getUserDto(UserEntity user);

    UserEntity getUserByEmail(String email);

    UserEntity enableUserByEmail(String email);

    UserEntity updateUserByEmail(String email, UserEntity updateUser);
}
