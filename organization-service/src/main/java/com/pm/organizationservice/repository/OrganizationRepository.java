package com.pm.organizationservice.repository;

import com.pm.organizationservice.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByRegistryNumber(String registryNumber);
    List<Organization> findByNormalizedOrganizationName(String normalizedOrganizationName);
    List<Organization> findByYearFoundedAndCompanySize(Integer yearFounded, Integer companySize);
}
