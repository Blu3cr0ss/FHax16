package idk.bluecross.fhax16.module.CLIENT

import idk.bluecross.fhax16.LOGGER
import idk.bluecross.fhax16.mc
import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.util.RenderUtil
import idk.bluecross.fhax16.util.RenderUtil.xPosFromPercentage
import idk.bluecross.fhax16.util.RenderUtil.yPosFromPercentage
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.opengl.GL11.*
import java.awt.Color

object Logo : Module(
    "Logo",
    "Displays our logo so every1 who looks at ur screen will see who is the cool kiddo here B)"
) {
    @SubscribeEvent
    fun onDraw(e: RenderGameOverlayEvent.Post) {        // SHOULD BE ONLY .POST !!!!
        mc.fontRenderer.drawString(
            e.matrixStack,
            "FHax16",
            xPosFromPercentage(.2),
            yPosFromPercentage(.2),
            Color.BLUE.hashCode()
        )
    }
}