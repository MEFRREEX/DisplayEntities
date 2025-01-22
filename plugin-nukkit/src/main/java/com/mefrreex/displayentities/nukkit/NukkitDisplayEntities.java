package com.mefrreex.displayentities.nukkit;

import com.mefrreex.displayentities.api.DisplayEntities;
import com.mefrreex.displayentities.api.entity.DisplayEntityManager;
import com.mefrreex.displayentities.nukkit.entity.NukkitDisplayEntityManager;

public class NukkitDisplayEntities implements DisplayEntities {

    private final DisplayEntityManager displayEntityManager;

    public NukkitDisplayEntities(NukkitDisplayEntitiesPlugin plugin) {
        this.displayEntityManager = new NukkitDisplayEntityManager(plugin);
    }

    @Override
    public DisplayEntityManager getDisplayEntityManager() {
        return displayEntityManager;
    }
}
