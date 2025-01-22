package com.mefrreex.displayentities.nukkit;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerToggleSneakEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.protocol.SetEntityDataPacket;
import cn.nukkit.plugin.PluginBase;
import com.mefrreex.displayentities.api.DisplayEntitiesProvider;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.core.entity.DisplayEntityStateImpl;
import com.mefrreex.displayentities.core.factory.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class NukkitDisplayEntitiesPlugin extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        DisplayEntitiesProvider.set(new NukkitDisplayEntities(this));
        this.getServer().getPluginManager().registerEvents(this, this); // TODO remove this
    }

    // Start the part of test code

    @EventHandler
    public void onJoin(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }

        Player player = event.getPlayer();

        DisplayEntityState state = DisplayEntityStateImpl.builder()
                .position(PositionFactory.builder()
                        .x((float) player.getX())
                        .y((float) player.getY())
                        .z((float) player.getZ())
                        .build())
                .basePosition(BasePositionFactory.empty())
                .rotation(RotationFactory.of(90f, 0f, 90f))
                .scale(ScaleFactory.ofScale(1f))
                .build();

        DisplayEntity entity = DisplayEntityFactory.createBlock("minecraft:grass", state);
        DisplayEntitiesProvider.get().getDisplayEntityManager().show(entity, player.getUniqueId());
    }

    @EventHandler
    public void onPacket(DataPacketSendEvent event) {
        if (event.getPacket() instanceof SetEntityDataPacket pk) {
            System.out.println(pk.metadata);
            System.out.println(pk.metadata.getMap());
        }
    }

    // End the test part of code

    public int getLegacyEntityId(String entityId) throws Exception {
        Class<?> addEntityPacketClass = AddEntityPacket.class;

        try {
            Field legacyIdsField = addEntityPacketClass.getDeclaredField("LEGACY_IDS");
            legacyIdsField.setAccessible(true);
            Map<Integer, String> legacyIds = (Map<Integer, String>) legacyIdsField.get(null);

            for (Map.Entry<Integer, String> entry : legacyIds.entrySet()) {
                if (entry.getValue().equals(entityId)) {
                    return entry.getKey();
                }
            }
        } catch (NoSuchFieldException e) {
            Method setupLegacyIdentifiersMethod = addEntityPacketClass.getDeclaredMethod("setupLegacyIdentifiers", Map.class, int.class);
            setupLegacyIdentifiersMethod.setAccessible(true);

            Map<Integer, String> mapping = new HashMap<>();
            setupLegacyIdentifiersMethod.invoke(null, mapping, ProtocolInfo.CURRENT_PROTOCOL);

            for (Map.Entry<Integer, String> entry : mapping.entrySet()) {
                if (entry.getValue().equals(entityId)) {
                    return entry.getKey();
                }
            }
        }

        throw new RuntimeException("Failed to retrieve legacy entity id");
    }
}
