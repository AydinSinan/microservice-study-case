package com.pm.userservice.service;

import com.pm.userservice.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByEmail(String email);
    List<UserDto> searchUsersByNormalizedName(String normalizedName);
    List<String> getUserOrganizations(Long userId);
    void addOrganizationsToUser(Long userId, List<Long> organizationIds);
}
