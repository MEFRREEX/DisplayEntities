package com.mefrreex.displayentities.api;

import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a class that stores an instance of the DisplayEntities class
 */
public class DisplayEntitiesProvider {

    private static DisplayEntities instance = null;

    /**
     * Returns the instance of the DisplayEntities provider.
     *
     * @return Instance of DisplayEntities
     * @throws IllegalStateException If no provider is set
     */
    public static DisplayEntities get() {
        if (instance == null) {
            throw new IllegalStateException("The DisplayEntities provider has not yet been set up");
        }
        return instance;
    }

    /**
     * Sets the DisplayEntities provider instance.
     *
     * @param instance New instance of DisplayEntities
     */
    @ApiStatus.Internal
    public static void set(DisplayEntities instance) {
        DisplayEntitiesProvider.instance = instance;
    }
}
