package com.pm.userservice.event;

import com.pm.common.events.UserOrganizationsUpdatedEvent;
import com.pm.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final UserRepository userRepository;

    @RabbitListener(queues = "user.organizations.update.queue")
    public void handleUserOrganizationsUpdatedEvent(UserOrganizationsUpdatedEvent event) {
        System.out.println("Received UserOrganizationsUpdatedEvent: " + event);

        userRepository.findByEmail(event.email()).ifPresent(user -> {
            System.out.println("Updated user with new organization names: " + event.organizationNames());
            userRepository.save(user);
        });
    }
}