package com.pm.organizationservice.service.impl;

import com.pm.organizationservice.dto.OrganizationDto;
import com.pm.organizationservice.entity.Organization;
import com.pm.organizationservice.exception.DuplicateRegistryNumberException;
import com.pm.organizationservice.exception.OrganizationNotFoundException;
import com.pm.organizationservice.repository.OrganizationRepository;
import com.pm.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;


    @Override
    public OrganizationDto createOrganization(OrganizationDto dto) {
        repository.findByRegistryNumber(dto.getRegistryNumber()).ifPresent(org -> {
            throw new DuplicateRegistryNumberException("Organization with registry number " + dto.getRegistryNumber() + " already exists.");
        });

        Organization org =Organization.builder()
                .organizationName(dto.getOrganizationName())
                .normalizedOrganizationName(dto.getOrganizationName().toLowerCase().replaceAll("[^a-z0-9]", ""))
                .registryNumber(dto.getRegistryNumber())
                .email(dto.getEmail())
                .companySize(dto.getCompanySize())
                .yearFounded(dto.getYearFounded())
                .build();

        Organization savedOrg = repository.save(org);
        return mapToDto(savedOrg);
    }

    @Override
    public OrganizationDto getByRegistryNumber(String registryNumber) {
        Organization org = repository.findByRegistryNumber(registryNumber)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization with registry number " + registryNumber + " not found."));
        return mapToDto(org);
    }

    @Override
    public OrganizationDto searchByNormalizedName(String name) {
        return (OrganizationDto) repository.findByNormalizedOrganizationName(name).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public OrganizationDto searchByYearAndSize(Integer year, Integer size) {
        return (OrganizationDto) repository.findByYearFoundedAndCompanySize(year, size).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private OrganizationDto mapToDto(Organization org) {
        return OrganizationDto.builder()
                .id(org.getId())
                .organizationName(org.getOrganizationName())
                .normalizedOrganizationName(org.getNormalizedOrganizationName())
                .registryNumber(org.getRegistryNumber())
                .email(org.getEmail())
                .companySize(org.getCompanySize())
                .yearFounded(org.getYearFounded())
                .build();
    }
}
