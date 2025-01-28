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

    private DisplayEntity entity;

    private float rotationX = -360;
    private float rotationZ = -360;

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new AllayDisplayEntities(this));
        Server.getInstance().getEventBus().registerListener(this);

        Server.getInstance().getScheduler().scheduleRepeating(this, () -> {
        if (entity == null) {
            return true;
        }

        if (rotationX >= 360) {
            rotationX = -360;
        }
        if (rotationZ >= 360) {
            rotationZ = -360;
        }
        rotationX += 0.5f;
        rotationZ += 0.5f;

        DisplayEntityState state = DisplayEntityStateImpl.builder()
                .rotation(RotationFactory.builder()
                        .x(rotationX)
                        .z(rotationZ)
                        .build())
                .build();
        DisplayEntitiesProvider.get().getDisplayEntityManager().updateForAll(entity, state);
        return true;
    }, 1);
}

@EventHandler
public void onChat(PlayerChatEvent event) {
    DisplayEntityState state = DisplayEntityStateImpl.builder()
            .entityPosition(EntityPositionFactory.builder()
                    .x(event.getPlayer().getLocation().x())
                    .y(event.getPlayer().getLocation().x())
                    .z(event.getPlayer().getLocation().x())
                    .build())
            .rotation(RotationFactory.builder()
                    .x(rotationX)
                    .x(rotationZ)
                    .build())
            .scale(ScaleFactory.ofScale(1f))
            .build();
    this.entity = DisplayEntityFactory.createBlock("minecraft:grass_block", state);
    DisplayEntitiesProvider.get().getDisplayEntityManager().show(entity, event.getPlayer().getUUID());
}
}
