package com.pm.organizationservice.service.impl;

import com.pm.organizationservice.dto.OrganizationDto;
import com.pm.organizationservice.entity.Organization;
import com.pm.organizationservice.event.OrganizationEventPublisher;
import com.pm.organizationservice.exception.DuplicateRegistryNumberException;
import com.pm.organizationservice.exception.OrganizationNotFoundException;
import com.pm.organizationservice.mapper.OrganizationMapper;
import com.pm.organizationservice.repository.OrganizationRepository;
import com.pm.organizationservice.service.OrganizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;
    private final OrganizationEventPublisher eventPublisher;


    @Override
    @Transactional
    public OrganizationDto createOrganization(OrganizationDto dto, String createdBy) {
        repository.findByRegistryNumber(dto.getRegistryNumber())
                .ifPresent(o -> {
                    throw new DuplicateRegistryNumberException("Registry number already in use: " + dto.getRegistryNumber());
                });

        Organization organization = mapper.toEntity(dto);

        if (organization.getEmail() == null) {
            log.warn("⚠️ email is null — organization will not be linked to a specific user");
        }

        // 3️⃣ Save to DB
        organization = repository.save(organization);
        log.info("✅ Organization '{}' saved successfully", organization.getOrganizationName());

        // 4️⃣ Aynı email’e ait organization'ları bul
        List<String> organizationNames = repository.findAllByEmail(organization.getEmail())
                .stream()
                .map(Organization::getOrganizationName)
                .toList();

        // 5️⃣ Event publish (user-service'e)
        eventPublisher.publishUserOrganizationsUpdatedEvent(
                organization.getEmail(),
                organizationNames
        );

        log.info("📤 Published UserOrganizationsUpdatedEvent for email={}, organizations={}",
                organization.getEmail(), organizationNames);

        // 6️⃣ DTO döndür
        return mapper.toDto(organization);
    }

    @Override
    public OrganizationDto getByRegistryNumber(String registryNumber) {
        return repository.findByRegistryNumber(registryNumber)
                .map(mapper::toDto)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found"));
    }

    @Override
    public Page<OrganizationDto> search(String normalizedName, Integer yearFounded, String companySize, Pageable pageable) {
        if (normalizedName != null) {
            return repository.findByNormalizedOrganizationNameContainingIgnoreCase(normalizedName, pageable)
                    .map(mapper::toDto);
        } else if (yearFounded != null) {
            return repository.findByYearFounded(yearFounded, pageable)
                    .map(mapper::toDto);
        } else if (companySize != null) {
            return repository.findByCompanySizeContainingIgnoreCase(companySize, pageable)
                    .map(mapper::toDto);
        } else {
            return repository.findAll(pageable)
                    .map(mapper::toDto);
        }
    }
}
