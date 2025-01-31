package com.mefrreex.displayentities.core.factory;

import com.mefrreex.displayentities.api.entity.DisplayBlock;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayBlockEntity;
import com.mefrreex.displayentities.core.entity.DisplayBlockEntityImpl;
import com.mefrreex.displayentities.core.entity.DisplayEntityImpl;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;

public class DisplayEntityFactory {

    public static DisplayEntity createEntity(String entityId, DisplayEntityState state) {
        return new DisplayEntityImpl(entityId, state);
    }

    public static DisplayBlockEntity createBlock(DisplayBlock block, DisplayEntityState state) {
        return new DisplayBlockEntityImpl(block, state);
    }
}
