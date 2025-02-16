package com.mefrreex.displayentities.core.entity.data;

import com.mefrreex.displayentities.api.entity.data.Scale;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class ScaleImpl implements Scale {

    private final Float scale;
    private final Float xzScale;
    private final Float yScale;

    public ScaleImpl(Float scale, Float xzScale, Float yScale) {
        this.scale = scale;
        this.xzScale = xzScale;
        this.yScale = yScale;
    }

    @Override
    public Float getScale() {
        return scale;
    }

    @Override
    public Float getXZScale() {
        return xzScale;
    }

    @Override
    public Float getYScale() {
        return yScale;
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        if (this.scale != null) {
            builder.append("temp.scale=").append(this.scale).append(";");
        }
        if (this.xzScale != null) {
            builder.append("temp.xzscale=").append(this.xzScale).append(";");
        }
        if (this.yScale != null) {
            builder.append("temp.yscale=").append(this.yScale).append(";");
        }
        return builder.toString();
    }
}
