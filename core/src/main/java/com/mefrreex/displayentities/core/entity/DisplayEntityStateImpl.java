package com.mefrreex.displayentities.core.entity;

import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.api.entity.data.*;
import lombok.Builder;

@Builder
public class DisplayEntityStateImpl implements DisplayEntityState {

    private final Position position;
    private final BasePosition basePosition;
    private final Rotation rotation;
    private final Scale scale;

    public DisplayEntityStateImpl(Position position, BasePosition basePosition, Rotation rotation, Scale scale) {
        this.position = position;
        this.basePosition = basePosition;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public BasePosition getBasePosition() {
        return basePosition;
    }

    @Override
    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public Scale getScale() {
        return scale;
    }
}
