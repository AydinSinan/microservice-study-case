package com.pm.userservice.dto;

import com.pm.userservice.enums.UserRole;
import com.pm.userservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String normalizedName;
    private UserRole role;
    private UserStatus status;
    private List<String> organizations;
}
