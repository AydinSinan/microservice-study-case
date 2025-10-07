package com.pm.invitationservice.dto;

import com.pm.invitationservice.enums.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDto {
    private Long id;
    private Long userId;
    private Long organizationId;
    private String invitationMessage;
    private InvitationStatus status;
    private LocalDateTime createdAt;
}
