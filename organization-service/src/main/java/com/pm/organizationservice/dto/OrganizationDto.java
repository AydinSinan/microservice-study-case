package com.pm.organizationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDto {
    private Long id;
    private String organizationName;
    private String normalizedOrganizationName;
    private String registryNumber;
    private String email;
    private Integer companySize;
    private Integer yearFounded;
}
