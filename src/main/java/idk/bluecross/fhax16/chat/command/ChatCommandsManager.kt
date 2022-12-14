package idk.bluecross.fhax16.chat.command

import idk.bluecross.fhax16.LOGGER
import idk.bluecross.fhax16.event.PacketEvent
import idk.bluecross.fhax16.util.ChatUtil
import net.minecraft.network.play.client.CChatMessagePacket
import net.minecraftforge.client.event.ClientChatEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.reflections.Reflections

object ChatCommandsManager {

    val commandsList = arrayListOf<AbstractCommand>()

    init {
        MinecraftForge.EVENT_BUS.register(this)
        // load all commands by reflection
        val classes =
            Reflections("idk.bluecross.fhax16.chat.command.commands").getSubTypesOf(AbstractCommand::class.java)
        classes.filter {
            !it.kotlin.isAbstract
        }.forEach {
            (it.classLoader.loadClass(it.canonicalName).kotlin.objectInstance as? AbstractCommand)?.let { it1 ->
                commandsList.add(
                    it1
                )
            }
        }
        LOGGER.info(commandsList)
    }

    var prefix = "!"

    @SubscribeEvent
    fun onMessage(e: ClientChatEvent) {
        var msg = e.message.trim()
        if (msg.startsWith(prefix)) {
            msg = msg.removePrefix(prefix)
            val cmd = commandsList.firstOrNull {
                it.cmd.lowercase() == msg.split(" ")[0].lowercase()
            }
            if (cmd != null) {
                ChatUtil.sendToMe("$msg -> Executing... ")
                if (msg.split(" ").size >= 2) {       // if we have args in our command (name arg)
                    cmd.process(ArrayList(msg.split(" ").subList(1, msg.split(" ").size).toList()))
                } else {  //if we havent enough args to do sublist
                    val list = ArrayList(msg.split(" ").toList())
                    list.removeFirst() // cmd name
                    cmd.process(list)       // this way its may be error but in Command class, where its handled so its ok i think
                }
            } else {
                ChatUtil.sendToMe("Command not found")
            }
        }
    }

    @SubscribeEvent
    fun onPacket(e: PacketEvent.Send) {
        if (e.packet is CChatMessagePacket && e.packet.message.trim().startsWith(prefix)) {
            e.cancel()
        }
    }
}
