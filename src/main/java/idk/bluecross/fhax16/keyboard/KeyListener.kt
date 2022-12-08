package idk.bluecross.fhax16.keyboard

import idk.bluecross.fhax16.LOGGER
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardKeyPressedEvent
import net.minecraftforge.client.event.InputEvent.KeyInputEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.glfw.GLFW


object KeyListener {
    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onKey(e: KeyInputEvent) {         // TODO: rewrite to new event manager
        if (e.key != GLFW.GLFW_KEY_UNKNOWN) {
            binds[e.key]?.invoke()
        }
    }
}