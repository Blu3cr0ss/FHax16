package idk.bluecross.fhax16.mixin.impl;

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
    @Inject(method = "onEntityCollision", at = @At(("HEAD")), cancellable = true)
    public void cancelSlowness(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_, CallbackInfo ci) {
        if (NoSlow.INSTANCE.isEnabled() && NoSlow.INSTANCE.getCobweb().value()) {
            ci.cancel();
        }
    }
}
