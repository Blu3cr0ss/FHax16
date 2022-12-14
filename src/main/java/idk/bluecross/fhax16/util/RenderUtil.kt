package idk.bluecross.fhax16.util

import idk.bluecross.fhax16.mc

object RenderUtil {
    fun xPosFromPercentage(percents: Double): Float {
        return mc.mainWindow.scaledWidth / 100 * percents.toFloat()
    }

    fun yPosFromPercentage(percents: Double): Float {
        return mc.mainWindow.scaledHeight / 100 * percents.toFloat()
    }
}