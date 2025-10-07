package com.pm.invitationservice.service;

import com.pm.invitationservice.dto.InvitationDto;
import com.pm.invitationservice.entity.Invitation;
import com.pm.invitationservice.enums.InvitationStatus;
import com.pm.invitationservice.mapper.InvitationMapper;
import com.pm.invitationservice.repository.InvitationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;

    @Transactional
    public InvitationDto createInvitation(InvitationDto invitationDto) {
        // check if there is pending invitation
        invitationRepository.findByUserIdAndOrganizationIdAndStatus(invitationDto.getUserId(), invitationDto.getOrganizationId(), InvitationStatus.PENDING)
                .ifPresent(invitation -> {
                    throw new RuntimeException("There is already a pending invitation for this user and organization");
                });

        // check if there is rejected invitation
        invitationRepository.findByUserIdAndOrganizationIdAndStatus(invitationDto.getUserId(), invitationDto.getOrganizationId(), InvitationStatus.REJECTED)
                .ifPresent(invitation -> {
                    throw new RuntimeException("There is already a rejected invitation for this user and organization");
                });

        Invitation entity = invitationMapper.toEntity(invitationDto);
        entity.setStatus(InvitationStatus.PENDING);
        entity.setCreatedAt(LocalDateTime.now());
        return invitationMapper.toDto(invitationRepository.save(entity));

    }

    @Transactional
    public void expireOldInvitations() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusDays(7);
        invitationRepository.findAll().stream()
                .filter(invitation -> invitation.getStatus() == InvitationStatus.PENDING)
                .filter(invitation -> invitation.getCreatedAt().isBefore(expirationThreshold))
                .forEach(invitation -> {
                    invitation.setStatus(InvitationStatus.EXPIRED);
                    invitationRepository.save(invitation);
                });
    }
}
