package com.mefrreex.displayentities.pnx;

import cn.nukkit.plugin.PluginBase;
import com.mefrreex.displayentities.api.DisplayEntitiesProvider;

public class PNXDisplayEntitiesPlugin extends PluginBase {

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new PNXDisplayEntities(this));
    }
}
