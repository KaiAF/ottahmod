package net.livzmc.ottah.client.render.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class OtterAnimations {
    public static Animation WALK;

    public OtterAnimations() {
    }

    static {
        WALK = Animation.Builder.create(1.0F).looping()
                .addBoneAnimation(
                        "front_right",
                        new Transformation(
                                Transformation.Targets.ROTATE,
                                new Keyframe(
                                        0f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        AnimationHelper.createRotationalVector(-20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                )
                        )
                )
                .addBoneAnimation(
                        "hind_right",
                        new Transformation(
                                Transformation.Targets.ROTATE,
                                new Keyframe(
                                        0f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        AnimationHelper.createRotationalVector(-20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                )
                        )
                )
                .addBoneAnimation(
                        "front_left",
                        new Transformation(
                                Transformation.Targets.ROTATE,
                                new Keyframe(
                                        0f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        AnimationHelper.createRotationalVector(-20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                )
                        )
                )
                .addBoneAnimation(
                        "hind_left",
                        new Transformation(
                                Transformation.Targets.ROTATE,
                                new Keyframe(
                                        0f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        AnimationHelper.createRotationalVector(-20f, 0f, 0f), Transformation.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR
                                )
                        )
                ).build();
    }
}