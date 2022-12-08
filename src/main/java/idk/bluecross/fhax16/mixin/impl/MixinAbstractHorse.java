package idk.bluecross.fhax16.mixin.impl;

import idk.bluecross.fhax16.module.MISC.HorseControl;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public class MixinAbstractHorse {
    @Inject(method = "isHorseSaddled", at = @At("HEAD"), cancellable = true)
    public void entityControl(CallbackInfoReturnable<Boolean> cir) {
        if (HorseControl.INSTANCE.isEnabled()) {
            cir.setReturnValue(true);
        }
    }
}
