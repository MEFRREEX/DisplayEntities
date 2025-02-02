package com.mefrreex.displayentities.api.entity;

import org.jetbrains.annotations.NotNull;

public record DisplayBlock(@NotNull String id, Integer meta) {

    public static DisplayBlock of(@NotNull String id) {
        return of(id, null);
    }

    public static DisplayBlock of(@NotNull String id, Integer meta) {
        if (id == null) {
            throw new IllegalArgumentException("Block ID cannot be null");
        }
        return new DisplayBlock(id, meta);
    }
}