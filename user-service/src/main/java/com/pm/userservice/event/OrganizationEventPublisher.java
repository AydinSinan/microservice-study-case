package com.pm.userservice.event;

import com.pm.common.events.OrganizationCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;;

@Service
@RequiredArgsConstructor
public class OrganizationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrganizationCreateEvent(OrganizationCreateEvent event) {
        rabbitTemplate.convertAndSend("organization.exchange", "organization.create", event);

        System.out.println("📤 Sent OrganizationCreateEvent: {}" + event);

    }
}
