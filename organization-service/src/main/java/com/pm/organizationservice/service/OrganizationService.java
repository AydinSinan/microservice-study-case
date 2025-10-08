package com.pm.organizationservice.service;

import com.pm.organizationservice.dto.OrganizationDto;

public interface OrganizationService {

    OrganizationDto createOrganization(OrganizationDto organizationDto);
    OrganizationDto getByRegistryNumber(String registryNumber);
    OrganizationDto searchByNormalizedName(String name);
    OrganizationDto searchByYearAndSize(Integer year, Integer size);

}
