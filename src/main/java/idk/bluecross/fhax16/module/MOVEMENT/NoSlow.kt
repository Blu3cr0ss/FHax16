package idk.bluecross.fhax16.module.MOVEMENT

import idk.bluecross.fhax16.mc
import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.settings.BoolSetting
import idk.bluecross.fhax16.settings.ModeSetting
import idk.bluecross.fhax16.util.isRiding
import idk.bluecross.fhax16.util.isUsingItem
import net.minecraft.block.Blocks
import net.minecraft.network.play.client.CEntityActionPacket
import net.minecraft.network.play.client.CPlayerDiggingPacket
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.vector.Vector3d
import net.minecraftforge.client.event.InputUpdateEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object NoSlow : Module("NoSlow", "Allows you not to slow down in some cases") {
    val blocks = BoolSetting(this, "OnBlocks", true)
    val useItmes = BoolSetting(this, "OnUseItem", true)
    val sneak = BoolSetting(this, "WhenSneaking", false)
    val cobweb = BoolSetting(this, "Cobweb", false)
    val cancelSneak = BoolSetting(this,"CancelSneak",false)
    val bypassMode =
        ModeSetting(
            this,
            "Bypass",
            BYPASS.NONE,
            listOf(
                BYPASS.NONE,
                BYPASS.AAC,
                BYPASS.HYPIXEL
            )
        )

    enum class BYPASS {
        NONE, AAC, HYPIXEL
    }

    override fun onEnable() {
        if (blocks.value()) {
            Blocks.SOUL_SAND.speedFactor =
                1f   // this works bc of acesstransformer. look at resources/META-INF/fhax_at.cfg
            Blocks.HONEY_BLOCK.speedFactor = 1f
            Blocks.SLIME_BLOCK.slipperiness =
                0.495f  // pbobos dev said that 0.495 is normal speed (yes, not 0.6 as in code)
        }
    }

    @SubscribeEvent
    fun onInput(e: InputUpdateEvent) {
        if (mc.player?.isRiding() == true) return
        val a = useItmes.value()
        val b = mc.player?.isUsingItem() == true
        val c = sneak.value()
        val d = mc.player?.isSneaking == true
        if (a && b) {
            e.movementInput.moveStrafe *= 5f
            e.movementInput.moveForward *= 5f
            doBypass(e)
        }
        if (c && d) {
            e.movementInput.moveStrafe *= 5f
            e.movementInput.moveForward *= 5f
            if (cancelSneak.value()){
                mc.connection?.sendPacket(
                    CEntityActionPacket(mc.player, CEntityActionPacket.Action.RELEASE_SHIFT_KEY)
                )
            }
        }
    }

    fun doBypass(e: InputUpdateEvent) {
        when (bypassMode.value()) {
            BYPASS.NONE -> return

            BYPASS.AAC -> {
                mc.connection?.sendPacket(
                    CPlayerTryUseItemOnBlockPacket(
                        Hand.MAIN_HAND,
                        BlockRayTraceResult.createMiss(Vector3d.ZERO, Direction.UP, BlockPos(-1, -1, -1))
                    )
                )
            }

            BYPASS.HYPIXEL -> {
                mc.connection?.sendPacket(
                    CPlayerDiggingPacket(
                        CPlayerDiggingPacket.Action.RELEASE_USE_ITEM,
                        BlockPos(0, 0, 0),
                        Direction.DOWN
                    )
                )
            }
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
