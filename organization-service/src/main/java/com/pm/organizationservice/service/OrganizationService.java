package com.pm.organizationservice.service;

import com.pm.organizationservice.dto.OrganizationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {

    OrganizationDto createOrganization(OrganizationDto organizationDto, String createdBy);
    OrganizationDto getByRegistryNumber(String registryNumber);
    Page<OrganizationDto> search(String normalizedName, Integer yearFounded, String companySize, Pageable pageable);

}
