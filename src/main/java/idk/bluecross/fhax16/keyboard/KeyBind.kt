package idk.bluecross.fhax16.keyboard

val binds = hashMapOf<Int, ArrayList<() -> Unit>>()

class KeyBind(val key: Int, val lambda: () -> Unit) {
    init {
        binds[key]?.add(lambda)
    }
}