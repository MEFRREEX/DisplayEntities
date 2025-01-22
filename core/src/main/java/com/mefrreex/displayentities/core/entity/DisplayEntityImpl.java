package com.mefrreex.displayentities.core.entity;

import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;

public class DisplayEntityImpl implements DisplayEntity {

    private final String entityId;
    private DisplayEntityState state;

    public DisplayEntityImpl(String entityId, DisplayEntityState state) {
        this.entityId = entityId;
        this.state = state;
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public DisplayEntityState getState() {
        return state;
    }
}
