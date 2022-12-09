package idk.bluecross.fhax16.mixin.impl;

import idk.bluecross.fhax16.GlobalsKt;
import net.minecraft.client.MainWindow;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainWindow.class)
public class MixinMainWindow {
    @Final
    @Shadow
    private long handle;

    @Inject(method = "setWindowTitle", at = @At("HEAD"), cancellable = true)        // always set window title only to our
    public void titleIsFHax(String title, CallbackInfo ci) {
        GLFW.glfwSetWindowTitle(handle, "FHax16 - " + GlobalsKt.getMc().getSession().getUsername());
        ci.cancel();
    }
}
