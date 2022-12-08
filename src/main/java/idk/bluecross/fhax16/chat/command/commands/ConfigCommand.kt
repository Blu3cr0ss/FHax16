package idk.bluecross.fhax16.chat.command.commands

import idk.bluecross.fhax16.chat.command.AbstractCommand
import idk.bluecross.fhax16.config.ConfigManager
import idk.bluecross.fhax16.util.ChatUtil
import java.io.File

object ConfigCommand : AbstractCommand("config") {
    override fun process(args: ArrayList<String>) {
        try {
            val cfgName =
                arrayListOf(args.lastOrNull { it != "load" && it != "save" }?.removeSuffix(".fhaxcfg") ?: "default")
            when (args[0].lowercase()) {
                "load" -> Load.process(cfgName)
                "save" -> Save.process(cfgName)
                else -> ChatUtil.sendToMe("Something went wrong. Usage config load/save *name* (name is optional)")
            }
        } catch (e: Exception) {
            ChatUtil.sendToMe("Something went wrong. Usage config load/save *name* (name is optional)")
            e.printStackTrace()
        }
    }

    object Load : SubCommand() {
        override fun process(args: ArrayList<String>) {
            val file = File("FHax16/configs", args[0] + ".fhaxcfg")
            ConfigManager.getCfg(file)
        }
    }

    object Save : SubCommand() {
        override fun process(args: ArrayList<String>) {
            val file = File("FHax16/configs", args[0] + ".fhaxcfg")
            ConfigManager.saveCfg(file)
        }
    }
}