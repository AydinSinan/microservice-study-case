package com.pm.invitationservice.scheduler;

import com.pm.invitationservice.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class InvitationScheduler {

    private final InvitationService invitationService;

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    public void expireOldInvitations() {
        invitationService.expireOldInvitations();
    }
}
