package com.luantang.socialmediaanalytics.authentication.service.impl;

import com.luantang.socialmediaanalytics.authentication.dto.UserDto;
import com.luantang.socialmediaanalytics.authentication.exception.EmailAlreadyExistException;
import com.luantang.socialmediaanalytics.authentication.exception.UserNotFoundException;
import com.luantang.socialmediaanalytics.authentication.model.UserEntity;
import com.luantang.socialmediaanalytics.authentication.repository.UserRepository;
import com.luantang.socialmediaanalytics.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            return userRepository.save(user);
        }
        else {
            UserEntity foundedUser = userRepository.findByEmail(user.getEmail()).get();
            if(foundedUser.isEnabled()) {
                throw new EmailAlreadyExistException("Email already exist");
            }
            else {
                return foundedUser;
            }
        }
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getUserDtos() {
        List<UserEntity> userEntityList = getUsers();
        return userEntityList.stream().map(this::userEntityToDto).toList();
    }

    @Override
    public UserDto getUserDto(UserEntity user) {
        return userEntityToDto(user);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with associate email could not be found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with associate email could not be found"));
    }

    @Override
    public UserEntity enableUserByEmail(String email) {
        UserEntity updatedUser = getUserByEmail(email);
        updatedUser.setEnabled(true);
        return userRepository.save(updatedUser);
    }

    @Override
    public UserEntity updateUserByEmail(String email, UserEntity updateUser) {
        UserEntity updatedUser = getUserByEmail(email);
        updatedUser.setFirstname(updateUser.getFirstname());
        updatedUser.setLastname(updateUser.getLastname());
        updatedUser.setEmail(updateUser.getEmail());
        updatedUser.setEnabled(updateUser.isEnabled());
        updatedUser.setPassword(updateUser.getPassword());
        updatedUser.setRole(updateUser.getRole());
        updatedUser.setInspireTokens(updateUser.getInspireTokens());

        return userRepository.save(updatedUser);
    }

    private UserDto userEntityToDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setInspireTokens(user.getInspireTokens());
        return userDto;
    }
}
