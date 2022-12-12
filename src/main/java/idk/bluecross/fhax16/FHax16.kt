package idk.bluecross.fhax16

import idk.bluecross.fhax16.chat.command.ChatCommandsManager
import idk.bluecross.fhax16.config.ConfigManager
import idk.bluecross.fhax16.keyboard.KeyListener
import idk.bluecross.fhax16.module.Module
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.reflections.Reflections


@Mod("fhax16")
class FHax16 {
    init {
        FMLJavaModLoadingContext.get().modEventBus.addListener(::setup)
        FMLJavaModLoadingContext.get().modEventBus.addListener(::doClientStuff)
        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun setup(event: FMLCommonSetupEvent) {
        LOGGER.info("setup()")
        mc.mainWindow.setWindowTitle("")
    }

    private fun doClientStuff(event: FMLClientSetupEvent) {
        LOGGER.info("doClientStuff()")
        // init all modules by reflection
        val allClasses = Reflections("idk.bluecross.fhax16.module").getSubTypesOf(Module::class.java)
        allClasses.filter {
            !it.kotlin.isAbstract
        }
        LOGGER.info(allClasses)
        allClasses.forEach {
            (it.classLoader.loadClass(it.canonicalName).kotlin.objectInstance as? Module)?.let { module ->
                val pkg = it.`package`.name.split(".").last()
                module.category = module.getCategoryByString(pkg)
            }
        }
        //
        KeyListener //init
        ChatCommandsManager
        ConfigManager
        try {
            ConfigManager.getCfg()
            ConfigManager.getBinds()
        }catch (e:Exception){
            LOGGER.error("Failed loading cfg on startup")
        }
    }
}
