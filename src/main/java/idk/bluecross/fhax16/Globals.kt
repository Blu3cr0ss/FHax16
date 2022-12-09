package idk.bluecross.fhax16

import idk.bluecross.fhax16.module.Module
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager

val LOGGER = LogManager.getLogger("FHax16")
val mc = Minecraft.getInstance()
val modules = arrayListOf<Module>()
val player = mc.player