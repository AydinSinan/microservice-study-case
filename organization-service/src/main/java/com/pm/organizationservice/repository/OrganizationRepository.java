package com.pm.organizationservice.repository;

import com.pm.organizationservice.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByRegistryNumber(String registryNumber);
    Page<Organization> findByNormalizedOrganizationNameContainingIgnoreCase(String normalizedName, Pageable pageable);
    Page<Organization> findByYearFounded(Integer yearFounded, Pageable pageable);
    Page<Organization> findByCompanySizeContainingIgnoreCase(String companySize, Pageable pageable);
    List<Organization> findAllByEmail(String email);

}
