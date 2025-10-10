package com.pm.userservice.service.impl;

import com.pm.userservice.dto.UserDto;
import com.pm.userservice.entity.User;
import com.pm.userservice.event.OrganizationEventPublisher;
import com.pm.userservice.mapper.UserMapper;
import com.pm.userservice.repository.UserRepository;
import com.pm.userservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.pm.common.events.OrganizationCreateEvent;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationEventPublisher organizationEventPublisher;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email already exists: " + userDto.getEmail());
                });

        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);

        List<String> organizationNames = userDto.getOrganizations();

        if (organizationNames == null || organizationNames.isEmpty()) {
            throw new IllegalArgumentException("Organizations cannot be null or empty");
        }

        for (String organizationName : organizationNames) {
            OrganizationCreateEvent event = new OrganizationCreateEvent(user.getId(), organizationName, user.getEmail());
            organizationEventPublisher.sendOrganizationCreateEvent(event);
        }

        return userMapper.userDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.userDto(user);
    }

    @Override
    public List<UserDto> searchUsersByNormalizedName(String normalizedName) {
        List<User> users = userRepository.findByNormalizedName(normalizedName);
        return users.stream()
                .map(userMapper::userDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getUserOrganizations(Long userId) {
        throw new UnsupportedOperationException(
                "Organization data is managed by organization-service. " +
                        "Use organization-service endpoint to get user's organizations."
        );
    }
}
