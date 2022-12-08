package idk.bluecross.fhax16

import idk.bluecross.fhax16.chat.command.ChatCommandsManager
import idk.bluecross.fhax16.config.ConfigManager
import idk.bluecross.fhax16.keyboard.KeyListener
import idk.bluecross.fhax16.module.Module
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import org.reflections.Reflections
import java.lang.reflect.Modifier
import kotlin.concurrent.thread


@Mod("fhax16")
class FHax16 {
    init {
        FMLJavaModLoadingContext.get().modEventBus.addListener(::setup)
        FMLJavaModLoadingContext.get().modEventBus.addListener(::doClientStuff)
        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun setup(event: FMLCommonSetupEvent) {
        LOGGER.info("setup()")
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
            val module = (it.classLoader.loadClass(it.canonicalName).kotlin.objectInstance as Module)
            val pkg = it.`package`.name.split(".").last()
            LOGGER.info(pkg)
            module.category = module.getCategoryByString(pkg)
        }
        //
        KeyListener //init
        ChatCommandsManager
        ConfigManager
        event.enqueueWork(::setTitle)
    }

    private fun setTitle() {
        thread {
            mc.mainWindow.setWindowTitle("FHax16")
        }
    }
}
