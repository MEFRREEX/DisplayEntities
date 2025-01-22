package com.mefrreex.displayentities.nukkit.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.ByteEntityData;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.data.LongEntityData;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.InventorySlotPacket;
import cn.nukkit.network.protocol.MobEffectPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import cn.nukkit.potion.Effect;
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

        long entityRuntimeId = Entity.entityCount++;
        uniqueToEntityIdMap.put(displayEntity.getUniqueId(), entityRuntimeId);

        AddEntityPacket addEntityPacket = new AddEntityPacket();
        addEntityPacket.entityUniqueId = entityRuntimeId;
        addEntityPacket.entityRuntimeId = entityRuntimeId;
        try {
            addEntityPacket.type = plugin.getLegacyEntityId(displayEntity.getEntityId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public void showToAll() {

    }

    @Override
    public void hide(DisplayEntity displayEntity, UUID playerId) {
        Long entityRuntimeId = uniqueToEntityIdMap.get(displayEntity.getUniqueId());
    }

    @Override
    public void hideToAll() {

    }
}
