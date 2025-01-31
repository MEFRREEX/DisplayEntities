package com.mefrreex.displayentities.allay;

import com.mefrreex.displayentities.api.entity.DisplayBlock;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.core.factory.*;
import org.allaymc.api.block.data.BlockId;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerToggleSneakEvent;
import org.allaymc.api.plugin.Plugin;
import com.mefrreex.displayentities.api.DisplayEntitiesProvider;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import org.allaymc.api.server.Server;

public class AllayDisplayEntitiesPlugin extends Plugin {

    private DisplayEntity entity;

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new AllayDisplayEntities(this));
        Server.getInstance().getEventBus().registerListener(this);
    }

    @EventHandler
    public void onSkean(PlayerToggleSneakEvent event) {
        if (!event.isValue()) {
            return;
        }

        DisplayEntityState state = DisplayEntityStateFactory.builder()
                .entityPosition(EntityPositionFactory.builder()
                        .x((float) event.getPlayer().getLocation().x())
                        .y((float) event.getPlayer().getLocation().y())
                        .z((float) event.getPlayer().getLocation().z())
                        .build())
                .position(PositionFactory.empty())
                .basePosition(BasePositionFactory.empty())
                .rotation(RotationFactory.empty())
                .scale(ScaleFactory.ofScale(1f))
                .build();
        DisplayEntity displayEntity = DisplayEntityFactory.createBlock(DisplayBlock.of("minecraft:redstone_lamp", 1), state);
        DisplayEntitiesProvider.get().getDisplayEntityManager().show(displayEntity, event.getPlayer().getUUID());
    }
}
