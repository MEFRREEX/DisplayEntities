package com.mefrreex.displayentities.core.factory;

import com.mefrreex.displayentities.api.entity.data.BasePosition;
import com.mefrreex.displayentities.core.entity.data.BasePositionImpl;

public class BasePositionFactory {

    public static BasePosition of(Float x, Float y, Float z) {
        return new BasePositionImpl(x, y, z);
    }

    public static BasePosition empty() {
        return new BasePositionImpl(0f, 0f, 0f);
    }

    public static BasePositionImpl.BasePositionImplBuilder builder() {
        return BasePositionImpl.builder();
    }
}
