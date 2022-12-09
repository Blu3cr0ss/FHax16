package idk.bluecross.fhax16.module.MOVEMENT

import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.player
import idk.bluecross.fhax16.settings.BoolSetting
import idk.bluecross.fhax16.util.isSlowedByItem
import net.minecraft.block.Blocks
import net.minecraftforge.client.event.InputUpdateEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object NoSlow : Module("NoSlow", "Allows you not to slow down on some blocks") {
    val blocks = BoolSetting(this, "OnBlocks", true)
    val useItmes = BoolSetting(this, "OnUseItem", true)
    val sneak = BoolSetting(this, "WhenSneaking", false)
    override fun onEnable() {
        Blocks.SOUL_SAND
    }

    @SubscribeEvent
    fun onPacket(e: InputUpdateEvent) {
        if (player?.ridingEntity == null) return

        if (player.isSneaking && sneak.value() || useItmes.value() && player.isSlowedByItem()) {
            e.movementInput.moveStrafe *= 5
            e.movementInput.moveForward *= 5
        }
    }

}