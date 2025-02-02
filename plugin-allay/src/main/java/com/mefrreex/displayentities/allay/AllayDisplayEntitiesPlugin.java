package com.mefrreex.displayentities.allay;

import org.allaymc.api.plugin.Plugin;
import com.mefrreex.displayentities.api.DisplayEntitiesProvider;
import com.mefrreex.displayentities.api.entity.DisplayEntity;

public class AllayDisplayEntitiesPlugin extends Plugin {

    private DisplayEntity entity;

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new AllayDisplayEntities(this));
    }
}
