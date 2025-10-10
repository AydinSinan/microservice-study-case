package com.pm.organizationservice.event;

import com.pm.common.events.OrganizationCreateEvent;
import com.pm.common.events.UserOrganizationsUpdatedEvent;
import com.pm.organizationservice.config.RabbitMQConfig;
import com.pm.organizationservice.entity.Organization;
import com.pm.organizationservice.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationEventListener {

    private final OrganizationRepository organizationRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.ORG_CREATE_QUEUE)
    public void handleOrganizationCreateEvent(OrganizationCreateEvent event) {
        log.info("📩 Received OrganizationCreateEvent: organization='{}' for userId={}, email='{}'",
                event.organizationName(), event.userId(), event.email());

        try {
            // ✅ 1. Organization kaydı oluştur
            Organization organization = Organization.builder()
                    .organizationName(event.organizationName())
                    .registryNumber("AUTO-" + event.userId() + "-" + UUID.randomUUID())
                    .email(event.email())
                    .build();

            organizationRepository.save(organization);
            log.info("✅ Organization '{}' saved successfully for userId={}",
                    event.organizationName(), event.userId());

            // ✅ 2. user-service'e event gönder
            UserOrganizationsUpdatedEvent userEvent = new UserOrganizationsUpdatedEvent(
                    event.email(), // 🔥 userId burada set ediliyor
                    List.of(event.organizationName())
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.USER_EXCHANGE,
                    RabbitMQConfig.USER_ORG_UPDATED_ROUTING_KEY,
                    userEvent
            );

            log.info("📤 Sent UserOrganizationsUpdatedEvent: {}", userEvent);

        } catch (Exception e) {
            log.error("❌ Error saving organization for userId={}: {}", event.userId(), e.getMessage());
        }
    }
}
