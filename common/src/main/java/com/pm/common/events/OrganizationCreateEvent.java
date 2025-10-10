package com.pm.common.events;


import java.io.Serializable;

public record OrganizationCreateEvent(Long userId, String email, String organizationName) implements Serializable {}

