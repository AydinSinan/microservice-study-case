package com.pm.userservice.mapper;

import com.pm.userservice.dto.UserDto;
import com.pm.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto userDto(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .normalizedName(user.getNormalizedName())
                .status(user.getStatus())
                .role(user.getRole())
                .build();
    }

    public User toEntity(UserDto dto) {
        if (dto == null) return null;

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setNormalizedName(dto.getNormalizedName());
        user.setStatus(dto.getStatus());
        user.setRole(dto.getRole());

        user.setNormalizedName(normalizeName(dto.getFullName()));

        return user;
    }

    private String normalizeName(String fullName) {
        return fullName == null ? null
                : fullName.replaceAll("[^A-Za-z0-9]", "")
                .toLowerCase();
    }
}
