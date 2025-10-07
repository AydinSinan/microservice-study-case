package com.pm.invitationservice.controller;

import com.pm.invitationservice.dto.InvitationDto;
import com.pm.invitationservice.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationDto> create(@RequestBody InvitationDto dto) {
        return ResponseEntity.ok(invitationService.createInvitation(dto));
    }

    @GetMapping("/healthz")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Invitation Service is up");
    }

}
