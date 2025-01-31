package com.mefrreex.displayentities.pnx;

import com.mefrreex.displayentities.api.DisplayEntities;
import com.mefrreex.displayentities.api.entity.DisplayEntityManager;
import com.mefrreex.displayentities.pnx.entity.PNXDisplayEntityManager;

public class PNXDisplayEntities implements DisplayEntities {

    private final DisplayEntityManager displayEntityManager;

    public PNXDisplayEntities(PNXDisplayEntitiesPlugin plugin) {
        this.displayEntityManager = new PNXDisplayEntityManager(plugin);
    }

    @Override
    public DisplayEntityManager getDisplayEntityManager() {
        return displayEntityManager;
    }
}
