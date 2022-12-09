package idk.bluecross.fhax16.mixin.impl.noslow;

import idk.bluecross.fhax16.module.MOVEMENT.NoSlow;
import net.minecraft.block.BlockState;
import net.minecraft.block.WebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WebBlock.class)
public class MixinCobweb {
    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    public void cancelCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn, CallbackInfo ci) {
        if (NoSlow.INSTANCE.isEnabled()) ci.cancel();
    }
}
