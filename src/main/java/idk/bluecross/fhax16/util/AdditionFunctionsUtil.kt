package idk.bluecross.fhax16.util

import idk.bluecross.fhax16.mc
import idk.bluecross.fhax16.player
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity

fun Entity.isPlayer(): Boolean {
    return this as? PlayerEntity != null
}

fun Entity.asPlayer(): PlayerEntity? {
    return this as? PlayerEntity
}

fun PlayerEntity.isMe(): Boolean {
    return this == mc.player
}

fun PlayerEntity.isSlowedByItem(): Boolean {
    return player?.isHandActive ?: false
}