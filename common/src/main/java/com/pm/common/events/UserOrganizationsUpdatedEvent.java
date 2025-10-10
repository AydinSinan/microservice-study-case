package com.pm.common.events;

import java.io.Serializable;
import java.util.List;


public record UserOrganizationsUpdatedEvent (String email, List<String> organizationNames) implements Serializable {}

