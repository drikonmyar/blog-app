package com.backend.blog.service.impl;

import com.backend.blog.config.AppConstants;
import com.backend.blog.entity.Role;
import com.backend.blog.entity.User;
import com.backend.blog.exception.ResourceNotFoundException;
import com.backend.blog.payload.UserDto;
import com.backend.blog.repository.RoleRepository;
import com.backend.blog.repository.UserRepository;
import com.backend.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        //encoded password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepository.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> listOfUsers = this.userRepository.findAll();
        return listOfUsers.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user){
        return this.modelMapper.map(user, UserDto.class);
    }
}
