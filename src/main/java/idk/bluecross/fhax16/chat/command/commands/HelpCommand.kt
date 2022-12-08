package idk.bluecross.fhax16.chat.command.commands

import idk.bluecross.fhax16.chat.command.AbstractCommand
import idk.bluecross.fhax16.chat.command.ChatCommandsManager
import idk.bluecross.fhax16.util.ChatUtil

object HelpCommand : AbstractCommand("help") {
    override fun process(args: ArrayList<String>) {
        ChatUtil.sendToMe(
            "List of command: \n" + ChatCommandsManager.commandsList.map { " - " + it.cmd + "\n" }.toString()
                .replace(",", "")
                .removeSurrounding("[", "]")
        )
    }
}