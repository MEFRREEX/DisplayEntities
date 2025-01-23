package com.mefrreex.displayentities.core.entity;

import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.api.entity.data.*;
import lombok.Builder;

@Builder
public class DisplayEntityStateImpl implements DisplayEntityState {

    private final Position position;
    private final BasePosition basePosition;
    private final EntityPosition entityPosition;
    private final Rotation rotation;
    private final Scale scale;

    public DisplayEntityStateImpl(Position position, BasePosition basePosition, EntityPosition entityPosition, Rotation rotation, Scale scale) {
        this.position = position;
        this.basePosition = basePosition;
        this.entityPosition = entityPosition;
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
    public EntityPosition getEntityPosition() {
        return entityPosition;
    }

    @Override
    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public Scale getScale() {
        return scale;
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        if (position != null) {
            builder.append(position.serialize());
        }
        if (basePosition != null) {
            builder.append(basePosition.serialize());
        }
        if (rotation != null) {
            builder.append(rotation.serialize());
        }
        if (scale != null) {
            builder.append(scale.serialize());
        }
        return builder.toString();
    }
}
