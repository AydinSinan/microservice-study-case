package com.pm.organizationservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String organizationName;

    @Column(nullable = false, unique = true)
    private String registryNumber;

    @Column(nullable = false)
    private String normalizedOrganizationName;

    @Column(nullable = false)
    private String email;

    private Integer companySize;
    private Integer yearFounded;

}
