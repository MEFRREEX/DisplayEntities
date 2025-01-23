package com.mefrreex.displayentities.api.entity;

import java.util.UUID;

public interface DisplayEntityManager {

    void show(DisplayEntity displayEntity, UUID playerId);

    void showToAll(DisplayEntity displayEntity);

    void hide(DisplayEntity displayEntity, UUID playerId);

    void hideToAll(DisplayEntity displayEntity);
}
