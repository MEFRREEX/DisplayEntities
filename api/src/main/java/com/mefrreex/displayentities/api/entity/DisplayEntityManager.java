package com.mefrreex.displayentities.api.entity;

import java.util.UUID;

public interface DisplayEntityManager {

    void show(DisplayEntity displayEntity, UUID playerId);

    void showToAll(DisplayEntity displayEntity);

    void update(DisplayEntity displayEntity, DisplayEntityState displayEntityState, UUID playerId);

    void updateForAll(DisplayEntity displayEntity, DisplayEntityState displayEntityState);

    void hide(DisplayEntity displayEntity, UUID playerId);

    void hideForAll(DisplayEntity displayEntity);
}
