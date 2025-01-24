package com.mefrreex.displayentities.nukkit.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.data.LongEntityData;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.*;
import cn.nukkit.network.protocol.AnimateEntityPacket.Animation;
import com.mefrreex.displayentities.api.entity.DisplayBlockEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityManager;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.core.entity.DisplayEntityMoLangVariables;
import com.mefrreex.displayentities.nukkit.NukkitDisplayEntitiesPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NukkitDisplayEntityManager implements DisplayEntityManager {

    private final NukkitDisplayEntitiesPlugin plugin;

    private final Map<UUID, Long> uniqueToEntityIdMap = new HashMap<>();

    public NukkitDisplayEntityManager(NukkitDisplayEntitiesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void show(DisplayEntity displayEntity, UUID playerId) {
        DisplayEntityState state = displayEntity.getState();

        // Create new entity id and save it to map
        long entityRuntimeId = Entity.entityCount++;
        uniqueToEntityIdMap.put(displayEntity.getUniqueId(), entityRuntimeId);

        AddEntityPacket addEntityPacket = new AddEntityPacket();
        addEntityPacket.entityUniqueId = entityRuntimeId;
        addEntityPacket.entityRuntimeId = entityRuntimeId;
        addEntityPacket.type = this.getLegacyEntityId(displayEntity.getEntityId());
        addEntityPacket.x = state.getEntityPosition().getX();
        addEntityPacket.y = state.getEntityPosition().getY();
        addEntityPacket.z = state.getEntityPosition().getZ();

        EntityMetadata metadata = new EntityMetadata()
                .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0.001f)
                .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0.001f);
        if (displayEntity instanceof DisplayBlockEntity) {
            metadata.put(new LongEntityData(0, 281474976710688L)); //Magic value - Invisibility flag
        }
        addEntityPacket.metadata = metadata;

        plugin.getServer().getPlayer(playerId).ifPresent(player -> {
            player.dataPacket(addEntityPacket);

            // Sending animations to created entity
            player.getServer().getScheduler().scheduleDelayedTask(plugin, () -> {
                // Reposition and define display entity scale
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.player.sleeping")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.INIT.get(state))
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.creeper.swelling")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.SCALE.useState(state))
                        .controller("displayentities:scale")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.ender_dragon.neck_head_movement")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.SHIFT_POSITION.useState(state))
                        .controller("displayentities:shift_pos")
                        .build(), entityRuntimeId);

                // Define display entity rotation
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.warden.move")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.ROTATION_X.useState(state))
                        .controller("displayentities:xrot")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.player.attack.rotations")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.ROTATION_Z.useState(state))
                        .controller("displayentities:zrot")
                        .build(), entityRuntimeId);

                // Define display entity position
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.parrot.moving")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.POSITION_X.useState(state))
                        .controller("displayentities:xpos")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.minecart.move.v1.0")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.POSITION_Y.useState(state))
                        .controller("displayentities:ypos")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.parrot.dance")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression(DisplayEntityMoLangVariables.POSITION_Z.useState(state))
                        .controller("displayentities:zpos")
                        .build(), entityRuntimeId);
            }, 2);

            // Set the entity to the first slot of the item if DisplayEntity is a block
            if (displayEntity instanceof DisplayBlockEntity blockEntity) {
                MobEquipmentPacket mobEquipmentPacket = new MobEquipmentPacket();
                mobEquipmentPacket.eid = entityRuntimeId;
                mobEquipmentPacket.hotbarSlot = 0;
                mobEquipmentPacket.item = Item.fromString(blockEntity.getBlockId());
                player.dataPacket(mobEquipmentPacket);
            }
        });
    }

    @Override
    public void showToAll(DisplayEntity displayEntity) {
        for (Player player : plugin.getServer().getOnlinePlayers().values()) {
            this.show(displayEntity, player.getUniqueId());
        }
    }

    @Override
    public void hide(DisplayEntity displayEntity, UUID playerId) {
        Long entityRuntimeId = uniqueToEntityIdMap.get(displayEntity.getUniqueId());
        if (entityRuntimeId == null) {
            return;
        }
        RemoveEntityPacket packet = new RemoveEntityPacket();
        packet.eid = entityRuntimeId;
        plugin.getServer().getPlayer(playerId).ifPresent(player -> {
            player.dataPacket(packet);
        });
    }

    @Override
    public void hideForAll(DisplayEntity displayEntity) {
        for (Player player : plugin.getServer().getOnlinePlayers().values()) {
            this.hide(displayEntity, player.getUniqueId());
        }
    }

    private int getLegacyEntityId(String entityId) {
        Map<Integer, String> mapping = new HashMap<>();
        AddEntityPacket.setupLegacyIdentifiers(mapping, ProtocolInfo.CURRENT_PROTOCOL);

        for (Map.Entry<Integer, String> entry : mapping.entrySet()) {
            if (entry.getValue().equals(entityId)) {
                return entry.getKey();
            }
        }
        return 0;
    }

    private void sendAnimation(Player player, Animation animation, long entityRuntimeId) {
        AnimateEntityPacket packet = new AnimateEntityPacket();
        packet.parseFromAnimation(animation);
        packet.getEntityRuntimeIds().add(entityRuntimeId);
        player.dataPacket(packet);
    }
}
