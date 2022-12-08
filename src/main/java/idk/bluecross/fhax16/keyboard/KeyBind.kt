package idk.bluecross.fhax16.keyboard

val binds = hashMapOf<Int, () -> Unit>()

class KeyBind(val key: Int, val lambda: () -> Unit) {
    init {
        binds[key] = lambda
    }
}