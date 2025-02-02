package com.mefrreex.displayentities.core.entity;

import com.mefrreex.displayentities.api.entity.DisplayBlock;
import com.mefrreex.displayentities.api.entity.DisplayBlockEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;

import java.util.UUID;

public class DisplayBlockEntityImpl extends DisplayEntityImpl implements DisplayBlockEntity {

    private final DisplayBlock block;

    public DisplayBlockEntityImpl(DisplayBlock block, DisplayEntityState state) {
        this(UUID.randomUUID(), block, state);
    }

    public DisplayBlockEntityImpl(UUID uniqueId, DisplayBlock block, DisplayEntityState state) {
        super(uniqueId, BLOCK_ENTITY_ID, state);
        this.block = block;
    }

    @Override
    public DisplayBlock getBlock() {
        return block;
    }
}
