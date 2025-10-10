package com.pm.organizationservice.event;

import com.pm.common.events.UserOrganizationsUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishUserOrganizationsUpdatedEvent(String email, List<String> organizationNames) {
        UserOrganizationsUpdatedEvent event = new UserOrganizationsUpdatedEvent(email, organizationNames);
        rabbitTemplate.convertAndSend(
                "user.exchange",           // target exchange
                "user.organizations.update", // routing key
                event
        );
        System.out.println("📤 Sent UserOrganizationsUpdatedEvent: " + event);
    }
}

