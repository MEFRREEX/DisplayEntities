package com.mefrreex.displayentities.allay.entity;

import com.mefrreex.displayentities.allay.AllayDisplayEntitiesPlugin;
import com.mefrreex.displayentities.allay.utils.Animation;
import com.mefrreex.displayentities.api.entity.DisplayBlockEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntity;
import com.mefrreex.displayentities.api.entity.DisplayEntityManager;
import com.mefrreex.displayentities.api.entity.DisplayEntityState;
import com.mefrreex.displayentities.api.entity.DisplayEntityMoLangVariables;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.entity.initinfo.EntityInitInfo;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.entity.type.EntityTypes;
import org.allaymc.api.registry.Registries;
import org.allaymc.api.server.Server;
import org.allaymc.api.utils.Identifier;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.AnimateEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import org.cloudburstmc.protocol.bedrock.packet.RemoveEntityPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AllayDisplayEntityManager implements DisplayEntityManager {

    private final AllayDisplayEntitiesPlugin plugin;

    private final Map<UUID, Long> uniqueToEntityIdMap = new HashMap<>();

    public AllayDisplayEntityManager(AllayDisplayEntitiesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void show(DisplayEntity displayEntity, UUID playerId) {
        DisplayEntityState state = displayEntity.getState();

        // Create new entity and save it id to map
        Entity entity = EntityTypes.FOX.createEntity(EntityInitInfo.builder()
                .pos(state.getEntityPosition().getX(),
                     state.getEntityPosition().getY(),
                     state.getEntityPosition().getZ()
                ).build());
        entity.setAndSendEntityFlag(EntityFlag.INVISIBLE, true);

        long entityRuntimeId = entity.getRuntimeId();
        uniqueToEntityIdMap.put(displayEntity.getUniqueId(), entityRuntimeId);

        EntityPlayer player = Server.getInstance().getOnlinePlayers().get(playerId);
        if (player != null) {
            entity.spawnTo(player);

            // Sending animations to created entity
            this.sendAnimations(player, state, entityRuntimeId);

            // Set the entity to the first slot of the item if DisplayEntity is a block
            if (displayEntity instanceof DisplayBlockEntity blockEntity) {
                MobEquipmentPacket mobEquipmentPacket = new MobEquipmentPacket();
                mobEquipmentPacket.setRuntimeEntityId(entityRuntimeId);
                mobEquipmentPacket.setHotbarSlot(0);
                mobEquipmentPacket.setItem(Registries.ITEMS.get(new Identifier(blockEntity.getBlockId())).createItemStack().toNetworkItemData());
                player.sendPacket(mobEquipmentPacket);
            }
        }
    }

    @Override
    public void showToAll(DisplayEntity displayEntity) {
        for (EntityPlayer player : Server.getInstance().getOnlinePlayers().values()) {
            this.show(displayEntity, player.getUUID());
        }
    }

    @Override
    public void update(DisplayEntity displayEntity, DisplayEntityState newState, UUID playerId) {
        Long entityRuntimeId = uniqueToEntityIdMap.get(displayEntity.getUniqueId());
        if (entityRuntimeId == null) {
            return;
        }

        DisplayEntityState oldState = displayEntity.getState();
        displayEntity.setState(newState);

        EntityPlayer player = Server.getInstance().getOnlinePlayers().get(playerId);
        if (player != null) {
            if (newState.getEntityPosition() != null && !oldState.getEntityPosition().equals(newState.getEntityPosition())) {
                MoveEntityAbsolutePacket packet = new MoveEntityAbsolutePacket();
                packet.setRuntimeEntityId(entityRuntimeId);
                packet.setForceMove(true);
                packet.setOnGround(true);
                packet.setTeleported(true);
                packet.setPosition(Vector3f.from(
                    newState.getEntityPosition().getX(),
                    newState.getEntityPosition().getY(),
                    newState.getEntityPosition().getZ()
                ));
                player.sendPacket(packet);
            }

            this.sendAnimations(player, newState, entityRuntimeId);
        }
    }

    @Override
    public void updateForAll(DisplayEntity displayEntity, DisplayEntityState newState) {
        for (EntityPlayer player : Server.getInstance().getOnlinePlayers().values()) {
            this.update(displayEntity, newState, player.getUUID());
        }
    }

    @Override
    public void hide(DisplayEntity displayEntity, UUID playerId) {
        Long entityRuntimeId = uniqueToEntityIdMap.get(displayEntity.getUniqueId());
        if (entityRuntimeId == null) {
            return;
        }
        RemoveEntityPacket packet = new RemoveEntityPacket();
        packet.setUniqueEntityId(entityRuntimeId);
        EntityPlayer player = Server.getInstance().getOnlinePlayers().get(playerId);
        if (player != null) {
            player.sendPacket(packet);
        }
    }

    @Override
    public void hideForAll(DisplayEntity displayEntity) {
        for (EntityPlayer player : Server.getInstance().getOnlinePlayers().values()) {
            this.hide(displayEntity, player.getUUID());
        }
    }

    private void sendAnimation(EntityPlayer player, Animation animation, long entityRuntimeId) {
        AnimateEntityPacket packet = new AnimateEntityPacket();
        packet.setAnimation(animation.getAnimation());
        packet.setNextState(animation.getNextState());
        packet.setBlendOutTime(animation.getBlendOutTime());
        packet.setStopExpression(animation.getStopExpression());
        packet.setStopExpressionVersion(animation.getStopExpressionVersion());
        packet.setController(animation.getController());
        packet.getRuntimeEntityIds().add(entityRuntimeId);
        player.sendPacket(packet);
    }

    public void sendAnimations(EntityPlayer player, DisplayEntityState state, long entityRuntimeId) {
        Server.getInstance().getScheduler().scheduleDelayed(plugin, () -> {
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
            return true;
        }, 2);
    }
}
