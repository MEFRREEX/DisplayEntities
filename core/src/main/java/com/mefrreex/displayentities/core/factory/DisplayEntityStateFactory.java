package com.mefrreex.displayentities.core.factory;

import com.mefrreex.displayentities.core.entity.DisplayEntityStateImpl;

public class DisplayEntityStateFactory {

    public static DisplayEntityStateImpl.DisplayEntityStateImplBuilder builder() {
        return DisplayEntityStateImpl.builder();
    }
}
