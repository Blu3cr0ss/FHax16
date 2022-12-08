package idk.bluecross.fhax16.chat.command

import idk.bluecross.fhax16.util.ChatUtil

abstract class AbstractCommand(val cmd: String) {
    abstract fun process(args: ArrayList<String>)
    abstract class SubCommand {
        abstract fun process(args: ArrayList<String>)
    }
}