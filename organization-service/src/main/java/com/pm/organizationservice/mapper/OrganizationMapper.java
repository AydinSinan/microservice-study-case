package com.pm.organizationservice.mapper;

import com.pm.organizationservice.dto.OrganizationDto;
import com.pm.organizationservice.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public Organization toEntity(OrganizationDto dto) {
        if (dto == null) return null;

        return Organization.builder()
                .organizationName(dto.getOrganizationName())
                .normalizedOrganizationName(normalize(dto.getNormalizedOrganizationName()))
                .registryNumber(dto.getRegistryNumber())
                .email(dto.getEmail())
                .companySize(dto.getCompanySize())
                .yearFounded(dto.getYearFounded())
                .build();
    }

    public OrganizationDto toDto(Organization entity) {
        if (entity == null) return null;

        return OrganizationDto.builder()
                .id(entity.getId())
                .organizationName(entity.getOrganizationName())
                .normalizedOrganizationName(entity.getNormalizedOrganizationName())
                .registryNumber(entity.getRegistryNumber())
                .email(entity.getEmail())
                .companySize(entity.getCompanySize())
                .yearFounded(entity.getYearFounded())
                .build();
    }

    private String normalize(String name) {
        return name == null ? null : name.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }
}