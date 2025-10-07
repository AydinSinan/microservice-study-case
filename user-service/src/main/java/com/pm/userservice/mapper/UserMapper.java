package com.pm.userservice.mapper;

import com.pm.userservice.dto.UserDto;
import com.pm.userservice.entity.Organization;
import com.pm.userservice.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto userDto(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setNormalizedName(user.getNormalizedName());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole());

        if (user.getOrganizations() != null) {
            List<String> organizationNames = user.getOrganizations().stream()
                    .map(Organization::getName)
                    .collect(Collectors.toList());
            dto.setOrganizations(organizationNames);
        }
        return dto;
    }

    public User toEntity(UserDto dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setNormalizedName(dto.getNormalizedName());
        user.setStatus(dto.getStatus());
        user.setRole(dto.getRole());

        return user;
    }
}
