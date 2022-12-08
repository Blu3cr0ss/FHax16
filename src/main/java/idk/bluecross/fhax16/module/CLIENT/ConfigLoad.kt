package idk.bluecross.fhax16.module.CLIENT

import idk.bluecross.fhax16.LOGGER
import idk.bluecross.fhax16.config.ConfigManager
import idk.bluecross.fhax16.module.Module
import org.lwjgl.glfw.GLFW

object ConfigLoad : Module("ConfigLoad","qwe", GLFW.GLFW_KEY_L){
    override fun onEnable() {
        ConfigManager.saveCfg()
    }
}