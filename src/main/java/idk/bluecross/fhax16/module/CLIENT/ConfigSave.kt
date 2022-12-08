package idk.bluecross.fhax16.module.CLIENT

import idk.bluecross.fhax16.LOGGER
import idk.bluecross.fhax16.config.ConfigManager
import idk.bluecross.fhax16.module.Module
import org.lwjgl.glfw.GLFW

object ConfigSave : Module("ConfigSave","qwe", GLFW.GLFW_KEY_K){
    override fun onEnable() {
        LOGGER.info(ConfigManager.getCfg())
    }
}