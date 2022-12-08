package idk.bluecross.fhax16.chat.command.commands

import idk.bluecross.fhax16.chat.command.AbstractCommand
import idk.bluecross.fhax16.chat.command.ChatCommandsManager
import idk.bluecross.fhax16.util.ChatUtil

object PrefixCommand : AbstractCommand("prefix") {
    override fun process(args: ArrayList<String>) {
        try {
            if (args.isEmpty()) {
                ChatCommandsManager.prefix = "!"
                ChatUtil.sendToMe("New prefix: ! (default)")
            } else {
                ChatCommandsManager.prefix = args[0]
                ChatUtil.sendToMe("New prefix: ${args[0]}")
            }
        } catch (e: Exception) {
            ChatUtil.sendToMe("Something went wrong. Usage: prefix *prefix* or just prefix - it will set prefix by default (!)")
        }
    }

}