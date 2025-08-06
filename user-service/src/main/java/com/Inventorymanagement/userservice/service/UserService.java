package com.Inventorymanagement.userservice.service;

import com.Inventorymanagement.userservice.dto.UserDto;
import com.Inventorymanagement.userservice.entity.User;
import com.Inventorymanagement.userservice.kafka.producer.UserKafkaProducer;
import com.Inventorymanagement.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserKafkaProducer kafkaProducer;

    public UserDto createUser(UserDto userDto){
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .address(userDto.getAddress())
                .build();

        User saved = userRepository.save(user);
        log.info("User created with ID: {}",saved.getId());
        kafkaProducer.sendUserCreatedEvent(saved);
         return mapToDto(saved);
    }


    public UserDto getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID:"+id));
        return mapToDto(user);
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    private UserDto mapToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }


}
