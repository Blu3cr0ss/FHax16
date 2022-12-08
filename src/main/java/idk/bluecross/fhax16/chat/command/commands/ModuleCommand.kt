package idk.bluecross.fhax16.chat.command.commands

import idk.bluecross.fhax16.LOGGER
import idk.bluecross.fhax16.chat.command.AbstractCommand
import idk.bluecross.fhax16.chat.command.ChatCommandsManager.prefix
import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.modules
import idk.bluecross.fhax16.settings.ISettingAbstract
import idk.bluecross.fhax16.settings.SettingPage
import idk.bluecross.fhax16.util.ChatUtil
import java.util.Arrays

object ModuleCommand : AbstractCommand("module") {
    override fun process(args: ArrayList<String>) {     // !module name disable/enable/setting
        try {
            val name = args[0].lowercase()
            if (name == "list") {     //if we got `module list` then return module list
                ChatUtil.sendToMe(modules.map(Module::name).toString().removeSurrounding("[", "]"))
                return
            }
            val otherArgs = args.subList(1, args.size).toList()
            when (otherArgs[0].lowercase()) {
                "disable" -> Disable.process(arrayListOf(name))
                "enable" -> Enable.process(arrayListOf(name))
                "info" -> Info.process(arrayListOf(name))

                "setting" -> {
                    val list = arrayListOf(name)
                    list.addAll(otherArgs.subList(1, otherArgs.size))
                    Setting.process(list)
                }

                "settings" -> {
                    val list = arrayListOf(name)
                    list.addAll(otherArgs.subList(1, otherArgs.size))
                    SettingList.process(list)
                }

                else -> {
                    ChatUtil.sendToMe("Something went wrong. Usage: ${prefix}module *name* enable/disable or ${prefix}module list or ${prefix}module *name* setting *name* *value*")
                }
            }
        } catch (e: Exception) {
            ChatUtil.sendToMe("Something went wrong. Usage: ${prefix}module *name* enable/disable or ${prefix}module list or ${prefix}module *name* setting *name* *value*")
            e.printStackTrace()
        }
    }

    object Disable : SubCommand() {
        override fun process(args: ArrayList<String>) {
            modules.first { it.name.lowercase() == args[0].lowercase() }.disable()
        }
    }

    object Enable : SubCommand() {
        override fun process(args: ArrayList<String>) {
            modules.first { it.name.lowercase() == args[0].lowercase() }.enable()
        }
    }

    object Info : SubCommand() {
        override fun process(args: ArrayList<String>) {
            val module = modules.first { it.name.lowercase() == args[0].lowercase() }
            val str = "name: ${module.name}\n" +
                    "description: ${module.description}\n" +
                    "category: ${module.category}\n" +
                    "enabled: ${module.isEnabled()}"
            ChatUtil.sendToMe(str)
        }
    }

    object Setting : SubCommand() {
        override fun process(args: ArrayList<String>) {
            val name = args[0].lowercase()
            val settingName = args[1].lowercase()
            val value = args[2].lowercase()      // value
            val setting = modules.first {
                it.name.lowercase() == name.lowercase()
            }.settings.firstOrNull {
                it.name.lowercase() == settingName.lowercase()
            }
            ChatUtil.sendToMe(setting?.setValByString(value) ?: "setting not found")
        }
    }

    object SettingList : SubCommand() {
        override fun process(args: ArrayList<String>) {
            val name = args[0] // value
            val settings = modules.first {
                it.name == name
            }.settings
            var prettyList = ""
            settings.forEach {
                prettyList += it.getPrettyInfo() + "\n"
            }
            ChatUtil.sendToMe(prettyList.trim())
        }
    }
}