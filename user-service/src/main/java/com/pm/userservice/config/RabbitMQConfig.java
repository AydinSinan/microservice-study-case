package com.pm.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_ORG_UPDATE_QUEUE = "user.organizations.update.queue";
    public static final String USER_ORG_UPDATE_EXCHANGE = "user.exchange";
    public static final String USER_ORG_UPDATE_ROUTING_KEY = "user.organizations.update";

    @Bean
    public Queue userOrganizationsUpdateQueue() {
        return new Queue(USER_ORG_UPDATE_QUEUE, true);
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_ORG_UPDATE_EXCHANGE);
    }

    @Bean
    public Binding userOrganizationsUpdateBinding() {
        return BindingBuilder
                .bind(userOrganizationsUpdateQueue())
                .to(userExchange())
                .with(USER_ORG_UPDATE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
