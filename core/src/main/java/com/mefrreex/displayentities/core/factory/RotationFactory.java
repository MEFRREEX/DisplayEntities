package com.mefrreex.displayentities.core.factory;

import com.mefrreex.displayentities.api.entity.data.Rotation;
import com.mefrreex.displayentities.core.entity.data.RotationImpl;

public class RotationFactory {

    public static Rotation of(Float x, Float y, Float z) {
        return new RotationImpl(x, y, z);
    }

    public static Rotation empty() {
        return new RotationImpl(0f, 0f, 0f);
    }

    public static RotationImpl.RotationImplBuilder builder() {
        return RotationImpl.builder();
    }
}
