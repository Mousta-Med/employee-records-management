package com.med.backend.service.Impl;

import com.med.backend.exception.ResourceNotFoundException;
import com.med.backend.model.entity.User;
import com.med.backend.repository.UserRepository;
import com.med.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User save(User userReq) {
        User user = modelMapper.map(userReq, User.class);
        user = userRepository.save(user);
        return modelMapper.map(user, User.class);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(User -> modelMapper.map(User, User.class)).collect(Collectors.toList());
    }

    @Override
    public User findOne(UUID id) {
        return userRepository.findById(id)
                .map(User -> modelMapper.map(User, User.class)).orElseThrow(() -> new ResourceNotFoundException("User Not found with this: " + id));
    }

    @Override
    public User update(UUID id, User userReq) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found with this: " + id));
        BeanUtils.copyProperties(userReq, existingUser);
        existingUser.setId(id);
        return modelMapper.map(userRepository.save(existingUser), User.class);
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found with this: " + id));
        userRepository.deleteById(id);
    }
}
