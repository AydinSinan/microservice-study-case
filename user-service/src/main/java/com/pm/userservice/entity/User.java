package com.pm.userservice.entity;

import com.pm.userservice.enums.UserRole;
import com.pm.userservice.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "normalized_name", nullable = false)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Normalized name must contain only letters")
    private String normalizedName;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
