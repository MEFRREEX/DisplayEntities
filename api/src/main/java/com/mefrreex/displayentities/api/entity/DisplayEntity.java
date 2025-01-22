package com.mefrreex.displayentities.api.entity;

import java.util.UUID;

public interface DisplayEntity {

    UUID getUniqueId();

    String getEntityId();

    DisplayEntityState getState();
}
