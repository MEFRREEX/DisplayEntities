package com.mefrreex.displayentities.api.entity;

import com.mefrreex.displayentities.api.entity.data.*;

public interface DisplayEntityState extends Serializable {

    Position getPosition();

    BasePosition getBasePosition();

    EntityPosition getEntityPosition();

    Rotation getRotation();

    Scale getScale();
}
