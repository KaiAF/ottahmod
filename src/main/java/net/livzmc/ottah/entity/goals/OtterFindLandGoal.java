package net.livzmc.ottah.entity.goals;

import net.livzmc.ottah.entity.passive.Otter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class OtterFindLandGoal extends Goal {
    private final Otter mob;
    private final int maxInWaterTime;
    private int inWaterTime;
    private final double speedModifier;
    protected final ServerLevel level;
    private BlockPos landZone = null;

    public OtterFindLandGoal(Otter mob, double d) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
        maxInWaterTime = new Random().nextInt(100, 200);
        this.speedModifier = d;
        this.level = getServerLevel(mob);
    }

    public boolean canUse() {
        return this.mob.isInWater();
    }

    @Override
    public void stop() {
        this.inWaterTime = 0;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        inWaterTime++;

//        System.out.println("Current in water time: " + inWaterTime + " Current max in water time: " + maxInWaterTime);

        if (inWaterTime > maxInWaterTime) {
            findNonWaterBlock();
            if (landZone == null) return;
            this.mob.getNavigation().moveTo(landZone.getCenter().x, landZone.getCenter().y, landZone.getCenter().z, this.speedModifier);

            if (this.mob.getNavigation().isDone()) stop();
        }


    }

    private void findNonWaterBlock() {
        Vec3 pos = mob.position();
        int radius = 32;


        for (int x = (int) (pos.x - radius); x < pos.x + radius; x++) {
            for (int y = (int) (pos.y - radius); y < pos.y + radius; y++) {
                for (int z = (int) (pos.z - radius); z < pos.z + radius; z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (!level.getBlockState(blockPos).is(Blocks.AIR)
                            && !level.getBlockState(blockPos).is(Blocks.WATER)
                            && level.getBlockState(blockPos).is(BlockTags.REPLACEABLE_BY_TREES)) {
                        landZone = blockPos;
                        return;
                    }
                }
            }
        }
    }
}
