package com.mefrreex.displayentities.core.entity.data;

import com.mefrreex.displayentities.api.entity.data.Rotation;
import lombok.Builder;

@Builder
public class RotationImpl implements Rotation {

    private final Float x;
    private final Float z;

    public RotationImpl(Float x, Float z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public Float getX() {
        return x;
    }

    @Override
    public Float getZ() {
        return z;
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        if (this.x != null) {
            builder.append("v.xrot=").append(this.x).append(";");
        }
        if (this.z != null) {
            builder.append("v.zrot=").append(this.z).append(";");
        }
        return builder.toString();
    }
}
