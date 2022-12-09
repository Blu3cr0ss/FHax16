package idk.bluecross.fhax16.module.MISC

import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.settings.IntSetting
import org.lwjgl.glfw.GLFW

object HorseControl : Module("HorseControl", "Allows you to ride on entities even without saddle", GLFW.GLFW_KEY_H) {
    val q = IntSetting(this, "range", 4, 3, 10)
    // all stuff in -> idk.bluecross.fhax16.mixin.impl.MixinAbstractHorse
}