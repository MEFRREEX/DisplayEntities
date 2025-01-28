package com.mefrreex.displayentities.core.entity.data;

import com.mefrreex.displayentities.api.entity.data.EntityPosition;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class EntityPositionImpl implements EntityPosition {

    private final Float x;
    private final Float y;
    private final Float z;

    public EntityPositionImpl(Float x, Float y, Float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Float getX() {
        return x;
    }

    @Override
    public Float getY() {
        return y;
    }

    @Override
    public Float getZ() {
        return z;
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        if (this.x != null) {
            builder.append("temp.entityxpos=").append(this.x).append(";");
        }
        if (this.y != null) {
            builder.append("temp.entityypos=").append(this.y).append(";");
        }
        if (this.z != null) {
            builder.append("temp.entityzpos=").append(this.z).append(";");
        }
        return builder.toString();
    }
}
