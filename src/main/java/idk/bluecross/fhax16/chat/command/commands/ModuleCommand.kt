package idk.bluecross.fhax16.chat.command.commands

import idk.bluecross.fhax16.chat.command.AbstractCommand
import idk.bluecross.fhax16.chat.command.ChatCommandsManager.prefix
import idk.bluecross.fhax16.config.ConfigManager
import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.modules
import idk.bluecross.fhax16.util.ChatUtil

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
                    SettingList.process(list)
                }

                "bind" -> {
                    val list = arrayListOf(name)
                    list.addAll(otherArgs.subList(1, otherArgs.size))
                    Bind.process(list)
                    ConfigManager.saveBinds()
                }

                else -> {
                    ChatUtil.sendToMe("Something went wrong. Usage: ${prefix}module *name* enable/disable or ${prefix}module list or ${prefix}module *name* setting *name* *value* or module *name* info or module *name* bind *key*")
                }
            }
        } catch (e: Exception) {
            ChatUtil.sendToMe("Something went wrong. Usage: ${prefix}module *name* enable/disable or ${prefix}module list or ${prefix}module *name* setting *name* *value* or module *name* info or module *name* bind *key*")
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
                    "keybind: ${module.key}\n" +
                    "enabled: ${module.isEnabled()}"
            ChatUtil.sendToMe(str)
        }
    }

    object Setting : SubCommand() {
        override fun process(args: ArrayList<String>) {
            val name = args[0].lowercase()
            val settingName = args[1].lowercase()
            if (args.size < 3) {
                SettingList.process(arrayListOf(name));
                return
            }
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
                it.name.lowercase() == name.lowercase()
            }.settings
            var prettyList = ""
            settings.forEach {
                prettyList += it.getPrettyInfo() + "\n"
            }
            ChatUtil.sendToMe(prettyList.trim())
        }
    }

    object Bind : SubCommand() {
        override fun process(args: ArrayList<String>) {
            val name = args[0]
            val bind = args[1]
            if (bind.matches(Regex("^\\d+$")))
                modules.firstOrNull { it.name.lowercase() == name.lowercase() }?.setBind(bind.toInt())
                    ?: ChatUtil.sendToMe("module not found")
            else ChatUtil.sendToMe("For now we are only using GLFW key id's to bind modules. Here more: https://www.glfw.org/docs/3.3/group__keys.html")
        }
    }
}