package idk.bluecross.fhax16.util

import idk.bluecross.fhax16.mc
import net.minecraft.util.text.ITextComponent

object ChatUtil {
    fun sendToMe(str: Any) {
        try {
            mc.player?.sendMessage(ITextComponent.getTextComponentOrEmpty(str.toString()),null)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}