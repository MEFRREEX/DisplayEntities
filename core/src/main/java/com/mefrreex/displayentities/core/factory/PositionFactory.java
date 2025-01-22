package com.mefrreex.displayentities.core.factory;

import com.mefrreex.displayentities.api.entity.data.Position;
import com.mefrreex.displayentities.core.entity.data.PositionImpl;

public class PositionFactory {

    public static Position of(Float x, Float y, Float z) {
        return new PositionImpl(x, y, z);
    }

    public static Position empty() {
        return new PositionImpl(0f, 0f, 0f);
    }

    public static PositionImpl.PositionImplBuilder builder() {
        return PositionImpl.builder();
    }
}
