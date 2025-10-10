package com.pm.organizationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // User-service → Organization-service (Create organization)
    public static final String ORG_EXCHANGE = "organization.exchange";
    public static final String ORG_CREATE_QUEUE = "organization.create.queue";
    public static final String ORG_CREATE_ROUTING_KEY = "organization.create";

    // Organization-service → User-service (Notify updated organizations)
    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_ORG_UPDATED_ROUTING_KEY = "user.organizations.update"; // ⚠️ küçük düzeltme

    // JSON message converter (serialize/deserialize)
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    // ✅ Exchange - organization events
    @Bean
    public TopicExchange organizationExchange() {
        return new TopicExchange(ORG_EXCHANGE);
    }

    // ✅ Queue - organization.create
    @Bean
    public Queue organizationCreateQueue() {
        return new Queue(ORG_CREATE_QUEUE, true);
    }

    // ✅ Binding - bağlama (queue ↔ exchange)
    @Bean
    public Binding organizationCreateBinding(
            Queue organizationCreateQueue,
            @Qualifier("organizationExchange") TopicExchange organizationExchange) {
        return BindingBuilder
                .bind(organizationCreateQueue)
                .to(organizationExchange)
                .with(ORG_CREATE_ROUTING_KEY);
    }

    // ✅ userExchange'i silme — user-service'e event göndermek için gereklidir
    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }
}
