package com.mefrreex.displayentities.api.entity;

import com.mefrreex.displayentities.api.entity.data.BasePosition;
import com.mefrreex.displayentities.api.entity.data.Position;
import com.mefrreex.displayentities.api.entity.data.Rotation;
import com.mefrreex.displayentities.api.entity.data.Scale;

public interface DisplayEntityState {

    Position getPosition();

    BasePosition getBasePosition();

    Rotation getRotation();

    Scale getScale();
}
