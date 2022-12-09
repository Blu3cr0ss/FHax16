package idk.bluecross.fhax16.event

import net.minecraftforge.eventbus.api.Event

abstract class AbstractEvent : Event() {
    open fun cancel() {
        isCanceled = true
    }
}