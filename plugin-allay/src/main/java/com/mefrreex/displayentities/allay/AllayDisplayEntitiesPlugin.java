package com.mefrreex.displayentities.allay;

import com.mefrreex.displayentities.api.DisplayEntitiesProvider;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.core.entity.DisplayEntityStateImpl;
import com.mefrreex.displayentities.core.factory.*;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerChatEvent;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;

public class AllayDisplayEntitiesPlugin extends Plugin {

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new AllayDisplayEntities(this));
        Server.getInstance().getEventBus().registerListener(this);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if (event.getMessage().contains("start")) {
            DisplayEntityState state1 = DisplayEntityStateImpl.builder()
                    .entityPosition(EntityPositionFactory.builder()
                            .x(event.getPlayer().getLocation().x())
                            .y(event.getPlayer().getLocation().y())
                            .z(event.getPlayer().getLocation().z())
                            .build())
                    .position(PositionFactory.empty())
                    .basePosition(BasePositionFactory.empty())
                    .rotation(RotationFactory.empty())
                    .scale(ScaleFactory.ofScale(1f))
                    .build();
            DisplayEntity entity = DisplayEntityFactory.createBlock("minecraft:grass_block", state1);
            DisplayEntitiesProvider.get().getDisplayEntityManager().show(entity, event.getPlayer().getUUID());
        }
    }
}
