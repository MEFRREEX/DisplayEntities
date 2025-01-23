package com.mefrreex.displayentities.nukkit.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.data.LongEntityData;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.*;
import cn.nukkit.network.protocol.AnimateEntityPacket.Animation;
import cn.nukkit.plugin.InternalPlugin;
import com.mefrreex.displayentities.api.entity.DisplayBlockEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityManager;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;
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
        addEntityPacket.x = state.getPosition().getX();
        addEntityPacket.y = state.getPosition().getY();
        addEntityPacket.z = state.getPosition().getZ();

        EntityMetadata metadata = new EntityMetadata()
                .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 1)
                .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 1);
        if (displayEntity instanceof DisplayBlockEntity) {
            metadata.put(new LongEntityData(0, 281474976710688L)); //Magic value - Invisibility flag
        }
        addEntityPacket.metadata = metadata;

        plugin.getServer().getPlayer(playerId).ifPresent(player -> {
            player.dataPacket(addEntityPacket);

            // Sending animations to created entity
            player.getServer().getScheduler().scheduleDelayedTask(InternalPlugin.INSTANCE, () -> {
                // Reposition and define display entity scale
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.player.sleeping")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("controller.animation.fox.move")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.creeper.swelling")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("v.xbasepos=v.xbasepos??0;v.ybasepos=v.ybasepos??0;v.zbasepos=v.zbasepos??0;v.xpos=v.xpos??0;v.ypos=v.ypos??0;v.zpos=v.zpos??0;v.xrot=v.xrot??0;v.yrot=v.yrot??0;v.zrot=v.zrot??0;v.scale=v.scale??1;v.xzscale=v.xzscale??1;v.yscale=v.yscale??1;v.swelling_scale1=2.1385*math.sqrt(v.xzscale)*math.sqrt(v.scale);v.swelling_scale2=2.1385*math.sqrt(v.yscale)*math.sqrt(v.scale);")
                        .controller("displayentities:scale")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.ender_dragon.neck_head_movement")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("v.head_rotation_x=0;v.head_rotation_y=0;v.head_rotation_z=0;v.head_position_x=(v.xbasepos*3741/8000)*math.sqrt(v.xzscale)*math.sqrt(v.scale);v.head_position_y=(10.6925+v.ybasepos*3741/8000)*math.sqrt(v.yscale)*math.sqrt(v.scale);v.head_position_z=(17.108-v.zbasepos*3741/8000)*math.sqrt(v.xzscale)*math.sqrt(v.scale);")
                        .controller("displayentities:shift_pos")
                        .build(), entityRuntimeId);

                // Define display entity rotation
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.warden.move")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("v.body_x_rot=90+v.xrot;v.body_z_rot=90+v.yrot;")
                        .controller("displayentities:xrot")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.player.attack.rotations")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("v.attack_body_rot_y=-v.zrot;")
                        .controller("displayentities:zrot")
                        .build(), entityRuntimeId);

                // Define display entity position
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.parrot.moving")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("v.wing_flap=(16-v.xpos)/0.3;")
                        .controller("displayentities:xpos")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.minecart.move.v1.0")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("v.rail_offset.x=0;v.rail_offset.y=1.6485+v.ypos/16;v.rail_offset.z=0;")
                        .controller("displayentities:ypos")
                        .build(), entityRuntimeId);
                this.sendAnimation(player, Animation.builder()
                        .animation("animation.parrot.dance")
                        .nextState("none")
                        .blendOutTime(0)
                        .stopExpression("v.dance.x=-v.zpos;v.dance.y=0;")
                        .controller("displayentities:zpos")
                        .build(), entityRuntimeId);
            }, 2);

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
    public void hideToAll(DisplayEntity displayEntity) {
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
