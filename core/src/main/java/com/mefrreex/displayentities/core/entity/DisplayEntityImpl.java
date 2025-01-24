package com.mefrreex.displayentities.core.entity;

import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;

import java.util.UUID;

public class DisplayEntityImpl implements DisplayEntity {

    private final UUID uniqueId;
    private final String entityId;
    private DisplayEntityState state;

    public DisplayEntityImpl(String entityId, DisplayEntityState state) {
        this(UUID.randomUUID(), entityId, state);
    }

    public DisplayEntityImpl(UUID uniqueId, String entityId, DisplayEntityState state) {
        this.uniqueId = uniqueId;
        this.entityId = entityId;
        this.state = state;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public DisplayEntityState getState() {
        return state;
    }

    @Override
    public void setState(DisplayEntityState state) {
        this.state = state;
    }
}
