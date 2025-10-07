package com.pm.invitationservice.mapper;

import com.pm.invitationservice.dto.InvitationDto;
import com.pm.invitationservice.entity.Invitation;
import org.springframework.stereotype.Component;

@Component
public class InvitationMapper {
    public InvitationDto toDto(Invitation invitation) {
        if (invitation == null) return null;

        return InvitationDto.builder()
                .id(invitation.getId())
                .userId(invitation.getUserId())
                .organizationId(invitation.getOrganizationId())
                .invitationMessage(invitation.getInvitationMessage())
                .status(invitation.getStatus())
                .createdAt(invitation.getCreatedAt())
                .build();
    }

    public Invitation toEntity(InvitationDto dto) {
        if (dto == null) return null;

        return Invitation.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .organizationId(dto.getOrganizationId())
                .invitationMessage(dto.getInvitationMessage())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
