package idk.bluecross.fhax16.mixin.impl.noslow;

import idk.bluecross.fhax16.module.MOVEMENT.NoSlow;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeBlock.class)
public class MixinSlimeBlock {
    @Inject(method = "onEntityWalk", at = @At("HEAD"), cancellable = true)
    public void cancelCollision(World worldIn, BlockPos pos, Entity entityIn, CallbackInfo ci) {
        if (NoSlow.INSTANCE.isEnabled()) ci.cancel();
    }
}
