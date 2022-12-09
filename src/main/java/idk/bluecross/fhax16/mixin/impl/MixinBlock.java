package idk.bluecross.fhax16.mixin.impl;

import idk.bluecross.fhax16.GlobalsKt;
import idk.bluecross.fhax16.module.MOVEMENT.NoSlow;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(method = "getSlipperiness", at = @At("HEAD"), cancellable = true)
    public void hook1(CallbackInfoReturnable<Float> cir) {
        if (GlobalsKt.getModules().isEmpty()) return;
        Block block = (Block) (Object) this;
        Block[] slowing = new Block[]{
                Blocks.SOUL_SAND
//                Blocks.SLIME_BLOCK,
//                Blocks.COBWEB
        };
        if (NoSlow.INSTANCE.isEnabled() && NoSlow.INSTANCE.getBlocks().value() && Arrays.asList(slowing).contains(block)) {
            cir.setReturnValue(0.6f);
        }
    }
}
