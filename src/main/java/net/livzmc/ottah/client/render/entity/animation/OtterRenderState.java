package net.livzmc.ottah.client.render.entity.animation;

import net.livzmc.ottah.client.render.entity.model.LeashedRenderState;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class OtterRenderState extends LivingEntityRenderState implements LeashedRenderState {
    public static AnimationDefinition WALK;
    public boolean isLeashed;

    static {
        WALK = AnimationDefinition.Builder.withLength(1.0F).looping()
                .addAnimation(
                        "front_right",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(
                                        0f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                )
                        )
                )
                .addAnimation(
                        "hind_right",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(
                                        0f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                )
                        )
                )
                .addAnimation(
                        "front_left",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(
                                        0f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                )
                        )
                )
                .addAnimation(
                        "hind_left",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(
                                        0f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.2916767f,
                                        KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.5834334f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        0.8343334f,
                                        KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                ),
                                new Keyframe(
                                        1f,
                                        KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR
                                )
                        )
                ).build();
    }

    @Override
    public boolean isLeashed() {
        return this.isLeashed;
    }
}