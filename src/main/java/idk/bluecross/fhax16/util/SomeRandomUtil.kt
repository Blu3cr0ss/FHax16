package idk.bluecross.fhax16.util

import idk.bluecross.fhax16.mc
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

fun Entity.isPlayer(): Boolean {
    return this as? PlayerEntity != null
}

fun Entity.asPlayer(): PlayerEntity? {
    return this as? PlayerEntity
}

fun PlayerEntity.isMe(): Boolean {
    return this == mc.player
}

fun PlayerEntity.isUsingItem(): Boolean {
    return this.isHandActive
}

fun PlayerEntity.isRiding(): Boolean {
    return this.ridingEntity != null
}

fun Any.toByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(this)
    objectOutputStream.flush()
    val result = byteArrayOutputStream.toByteArray()
    byteArrayOutputStream.close()
    objectOutputStream.close()
    return result
}