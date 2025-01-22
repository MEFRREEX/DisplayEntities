package com.mefrreex.displayentities.core.factory;

import com.mefrreex.displayentities.api.entity.data.Scale;
import com.mefrreex.displayentities.core.entity.data.ScaleImpl;

public class ScaleFactory {

    public static Scale of(Float scale, Float xzScale, Float yScale) {
        return new ScaleImpl(scale, xzScale, yScale);
    }

    public static Scale ofScale(float scale) {
        return new ScaleImpl(scale, null, null);
    }

    public static Scale ofXYZScale(float xz, float y) {
        return new ScaleImpl(null, xz, y);
    }

    public static Scale empty() {
        return new ScaleImpl(0f, 0f, 0f);
    }

    public static ScaleImpl.ScaleImplBuilder builder() {
        return ScaleImpl.builder();
    }
}