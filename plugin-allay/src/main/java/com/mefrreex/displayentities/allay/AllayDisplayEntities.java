package com.mefrreex.displayentities.allay;

import com.mefrreex.displayentities.allay.entity.AllayDisplayEntityManager;
import com.mefrreex.displayentities.api.DisplayEntities;
import com.mefrreex.displayentities.api.entity.DisplayEntityManager;

public class AllayDisplayEntities implements DisplayEntities {

    private final DisplayEntityManager displayEntityManager;

    public AllayDisplayEntities(AllayDisplayEntitiesPlugin plugin) {
        this.displayEntityManager = new AllayDisplayEntityManager(plugin);
    }

    @Override
    public DisplayEntityManager getDisplayEntityManager() {
        return displayEntityManager;
    }
}
