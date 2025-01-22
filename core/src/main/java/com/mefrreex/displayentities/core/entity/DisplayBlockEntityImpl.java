package com.mefrreex.displayentities.core.entity;

import com.mefrreex.displayentities.api.entity.DisplayBlockEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;

public class DisplayBlockEntityImpl extends DisplayEntityImpl implements DisplayBlockEntity {

    private final String blockId;

    public DisplayBlockEntityImpl(String blockId, DisplayEntityState state) {
        super("minecraft:fox", state);
        this.blockId = blockId;
    }

    @Override
    public String getBlockId() {
        return blockId;
    }
}
