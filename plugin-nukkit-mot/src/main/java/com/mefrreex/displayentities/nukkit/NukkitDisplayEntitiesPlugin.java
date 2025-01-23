package com.mefrreex.displayentities.nukkit;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import com.mefrreex.displayentities.api.DisplayEntitiesProvider;

public class NukkitDisplayEntitiesPlugin extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new NukkitDisplayEntities(this));
    }
}
