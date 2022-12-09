package idk.bluecross.fhax16.keyboard

val binds = ArrayList<KeyBind>()

class KeyBind(val key: Int, val lambda: () -> Unit) {
    init {
        binds.add(this)
    }
}