package com.mefrreex.displayentities.core.factory;

import com.mefrreex.displayentities.api.entity.data.EntityPosition;
import com.mefrreex.displayentities.core.entity.data.EntityPositionImpl;

public class EntityPositionFactory {

    public static EntityPosition of(Float x, Float y, Float z) {
        return new EntityPositionImpl(x, y, z);
    }

    public static EntityPosition empty() {
        return new EntityPositionImpl(0f, 0f, 0f);
    }

    public static EntityPositionImpl.EntityPositionImplBuilder builder() {
        return EntityPositionImpl.builder();
    }
}
