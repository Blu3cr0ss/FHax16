package idk.bluecross.fhax16.event

import net.minecraft.network.IPacket

open class PacketEvent(open val packet: IPacket<*>) : AbstractEvent() {
    override fun isCancelable(): Boolean {
        return true
    }

    // its looks kinda sussy
    class Send(override val packet: IPacket<*>) : PacketEvent(packet)
    class Get(override val packet: IPacket<*>) : PacketEvent(packet)
}