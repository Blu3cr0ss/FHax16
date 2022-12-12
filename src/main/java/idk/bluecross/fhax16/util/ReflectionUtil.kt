package idk.bluecross.fhax16.util

object ReflectionUtil {

}
fun Any.setField(name: String, value: Any) {
    val field = this::class.java.getDeclaredField(name)
    field.isAccessible = true
    field.set(this, value)
}