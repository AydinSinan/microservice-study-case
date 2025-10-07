package com.pm.userservice.service.impl;

import com.pm.userservice.dto.UserDto;
import com.pm.userservice.entity.Organization;
import com.pm.userservice.entity.User;
import com.pm.userservice.mapper.UserMapper;
import com.pm.userservice.repository.OrganizationRepository;
import com.pm.userservice.repository.UserRepository;
import com.pm.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        List<String> organizationNames = userDto.getOrganizations();
        if (organizationNames == null || organizationNames.isEmpty()) {
            throw new IllegalArgumentException("Organizations cannot be null or empty");
        }

        List<Organization> existingOrganizations = organizationRepository.findByNameIn(organizationNames);

        // Find missing organizations
        List<String> existingOrganizationNames = existingOrganizations.stream()
                .map(Organization::getName)
                .toList();
        List<String> missingOrganizationNames = organizationNames.stream()
                .filter(name -> !existingOrganizationNames.contains(name))
                .toList();

        // Add missing organizations to the database
        List<Organization> newOrganizations = missingOrganizationNames.stream()
                .map(name -> {
                    Organization organization = new Organization();
                    organization.setName(name);
                    return organization;
                })
                .collect(Collectors.toList());
        organizationRepository.saveAll(newOrganizations);

        // Combine existing and new organizations
        existingOrganizations.addAll(newOrganizations);
        user.setOrganizations(existingOrganizations);
        return userMapper.userDto(userRepository.save(user));
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with Id: " + userId));
        return user.getOrganizations().stream()
                .map(Organization::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addOrganizationsToUser(Long userId, List<Long> organizationIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Organization> organizations = organizationRepository.findAllById(organizationIds);
        user.getOrganizations().addAll(organizations);

        userRepository.save(user);
    }

}
