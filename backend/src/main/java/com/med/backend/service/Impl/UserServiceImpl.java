package com.med.backend.service.Impl;

import com.med.backend.exception.ResourceNotFoundException;
import com.med.backend.model.entity.User;
import com.med.backend.model.enums.Role;
import com.med.backend.model.request.UserReq;
import com.med.backend.model.response.UserRes;
import com.med.backend.repository.UserRepository;
import com.med.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String rawPassword) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .build();
        userRepository.save(user);
    }

    @Override
    public UserRes save(UserReq userReq) {
        User user = modelMapper.map(userReq, User.class);
        user = userRepository.save(user);
        return modelMapper.map(user, UserRes.class);
    }

    @Override
    public List<UserRes> findAll() {
        return userRepository.findAll().stream().map(User -> modelMapper.map(User, UserRes.class)).collect(Collectors.toList());
    }

    @Override
    public UserRes findOne(UUID id) {
        return userRepository.findById(id)
                .map(User -> modelMapper.map(User, UserRes.class)).orElseThrow(() -> new ResourceNotFoundException("User Not found with this: " + id));
    }

    @Override
    public UserRes update(UUID id, UserReq userReq) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found with this: " + id));
        BeanUtils.copyProperties(userReq, existingUser);
        existingUser.setId(id);
        return modelMapper.map(userRepository.save(existingUser), UserRes.class);
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found with this: " + id));
        userRepository.deleteById(id);
    }

    @Override
    public UserRes updateRole(UUID id, Role role) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found with this: " + id));
        existingUser.setRole(role);
        return modelMapper.map(userRepository.save(existingUser), UserRes.class);
    }
}
