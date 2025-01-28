package com.mefrreex.displayentities.core.entity.data;

import com.mefrreex.displayentities.api.entity.data.Position;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class PositionImpl implements Position {

    private final Float x;
    private final Float y;
    private final Float z;

    public PositionImpl(Float x, Float y, Float z) {
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
            builder.append("temp.xpos=").append(this.x).append(";");
        }
        if (this.y != null) {
            builder.append("temp.ypos=").append(this.y).append(";");
        }
        if (this.z != null) {
            builder.append("temp.zpos=").append(this.z).append(";");
        }
        return builder.toString();
    }
}
