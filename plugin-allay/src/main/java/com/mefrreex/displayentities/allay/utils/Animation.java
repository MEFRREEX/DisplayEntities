package com.mefrreex.displayentities.allay.utils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Animation {

    private String animation;

    @Builder.Default
    private String nextState = "default";

    @Builder.Default
    private float blendOutTime = 0.0f;

    @Builder.Default
    private String stopExpression = "query.any_animation_finished";

    @Builder.Default
    private String controller = "__runtime_controller";

    @Builder.Default
    private int stopExpressionVersion = 16777216;
}