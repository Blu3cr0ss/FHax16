package idk.bluecross.fhax16.module.MOVEMENT

import idk.bluecross.fhax16.mc
import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.settings.BoolSetting
import idk.bluecross.fhax16.util.isRiding
import idk.bluecross.fhax16.util.isUsingItem
import net.minecraft.block.Blocks
import net.minecraftforge.client.event.InputUpdateEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object NoSlow : Module("NoSlow", "Allows you not to slow down in some cases") {
    val blocks = BoolSetting(this, "OnBlocks", true)
    val useItmes = BoolSetting(this, "OnUseItem", true)
    val sneak = BoolSetting(this, "WhenSneaking", false)
    val cobweb = BoolSetting(this, "Cobweb", false)

    override fun onEnable() {
        if (blocks.value()) {
            Blocks.SOUL_SAND.speedFactor =
                1f   // this works bc of acesstransformer. look at resources/META-INF/fhax_at.cfg
            Blocks.HONEY_BLOCK.speedFactor = 1f
            Blocks.SLIME_BLOCK.slipperiness =
                0.4f  // pbobos dev said that 0.495 is normal speed (yes, not 0.6 as in code) nut ive decided 0.4 is better
        }
    }

    @SubscribeEvent
    fun onInput(e: InputUpdateEvent) {
        if (mc.player?.isRiding() == true) return
        val a = useItmes.value()
        val b = mc.player?.isUsingItem() == true
        val c = sneak.value()
        val d = mc.player?.isSneaking == true
        if (a && b || c && d) {
            e.movementInput.moveStrafe *= 5f
            e.movementInput.moveForward *= 5f
        }
    }

    override fun onDisable() {
        if (blocks.value()) {
            Blocks.SOUL_SAND.speedFactor = 0.4f
            Blocks.HONEY_BLOCK.speedFactor = 0.4f
            Blocks.SLIME_BLOCK.slipperiness = 0.8f
        }
    }
}
