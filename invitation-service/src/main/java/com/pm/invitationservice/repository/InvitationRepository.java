package com.pm.invitationservice.repository;

import com.pm.invitationservice.entity.Invitation;
import com.pm.invitationservice.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByUserIdAndOrganizationIdAndStatus(Long userId, Long organizationId, InvitationStatus status);
}
