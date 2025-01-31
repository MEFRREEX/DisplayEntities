package com.mefrreex.displayentities.pnx;

import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerToggleSneakEvent;
import cn.nukkit.plugin.PluginBase;
import com.mefrreex.displayentities.api.DisplayEntitiesProvider;
import com.mefrreex.displayentities.api.entity.DisplayBlock;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.core.factory.*;

public class PNXDisplayEntitiesPlugin extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new PNXDisplayEntities(this));
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }
        DisplayEntityState state = DisplayEntityStateFactory.builder()
                .entityPosition(EntityPositionFactory.builder()
                        .x((float) event.getPlayer().getX())
                        .y((float) event.getPlayer().getY())
                        .z((float) event.getPlayer().getZ())
                        .build())
                .position(PositionFactory.empty())
                .basePosition(BasePositionFactory.empty())
                .rotation(RotationFactory.empty())
                .scale(ScaleFactory.ofScale(0.7f))
                .build();
        DisplayBlock block = DisplayBlock.of(BlockID.RED_SHULKER_BOX);
        DisplayEntity entity = DisplayEntityFactory.createBlock(block, state);
        DisplayEntitiesProvider.get().getDisplayEntityManager().show(entity, event.getPlayer().getUniqueId());
    }
}
