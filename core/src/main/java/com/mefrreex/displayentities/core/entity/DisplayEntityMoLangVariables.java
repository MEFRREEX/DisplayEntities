package com.mefrreex.displayentities.core.entity;

import com.mefrreex.displayentities.api.entity.DisplayEntityState;

public class DisplayEntityMoLangVariables {

    public static final DisplayEntityMoLangVariable INIT = state -> "controller.animation.fox.move";
    public static final DisplayEntityMoLangVariable SCALE = state -> "v.xbasepos=temp.xbasepos??0;v.ybasepos=temp.ybasepos??0;v.zbasepos=temp.zbasepos??0;v.xpos=temp.xpos??0;v.ypos=temp.ypos??0;v.zpos=temp.zpos??0;v.xrot=temp.xrot??0;v.yrot=temp.yrot??0;v.zrot=temp.zrot??0;v.scale=temp.scale??1;v.xzscale=temp.xzscale??1;v.yscale=temp.yscale??1;v.swelling_scale1=2.1385*math.sqrt(v.xzscale)*math.sqrt(v.scale);v.swelling_scale2=2.1385*math.sqrt(v.yscale)*math.sqrt(v.scale);";
    public static final DisplayEntityMoLangVariable SHIFT_POSITION = state -> "v.head_rotation_x=0;v.head_rotation_y=0;v.head_rotation_z=0;v.head_position_x=(v.xbasepos)*math.sqrt(v.xzscale)*math.sqrt(v.scale);v.head_position_y=(10.6925+v.ybasepos)*math.sqrt(v.yscale)*math.sqrt(v.scale);v.head_position_z=(17.108-v.zbasepos)*math.sqrt(v.xzscale)*math.sqrt(v.scale);";
    public static final DisplayEntityMoLangVariable ROTATION_X = state -> "v.body_x_rot=90+v.xrot;v.body_z_rot=90+v.yrot;";
    public static final DisplayEntityMoLangVariable ROTATION_Z = state -> "v.attack_body_rot_y=-v.zrot;";
    public static final DisplayEntityMoLangVariable POSITION_X = state -> "v.wing_flap=(16-v.xpos)/0.3;";
    public static final DisplayEntityMoLangVariable POSITION_Y = state -> "v.rail_offset.x=0;v.rail_offset.y=1.6485+v.ypos/16;v.rail_offset.z=0;";
    public static final DisplayEntityMoLangVariable POSITION_Z = state -> "v.dance.x=-v.zpos;v.dance.y=0;";

    @FunctionalInterface
    public interface DisplayEntityMoLangVariable {
        String get(DisplayEntityState state);

        default String useState(DisplayEntityState state) {
            return state.serialize() + this.get(state);
        }
    }
}
