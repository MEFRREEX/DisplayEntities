package com.mefrreex.displayentities.core.entity.data;

import com.mefrreex.displayentities.api.entity.data.BasePosition;
import lombok.Builder;

@Builder
public class BasePositionImpl implements BasePosition {

    private final Float x;
    private final Float y;
    private final Float z;

    public BasePositionImpl(Float x, Float y, Float z) {
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
            builder.append("temp.xbasepos=").append(this.x).append(";");
        }
        if (this.y != null) {
            builder.append("temp.ybasepos=").append(this.y).append(";");
        }
        if (this.z != null) {
            builder.append("temp.zbasepos=").append(this.z).append(";");
        }
        return builder.toString();
    }
}
