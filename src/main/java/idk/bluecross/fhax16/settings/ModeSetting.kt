package idk.bluecross.fhax16.settings

import idk.bluecross.fhax16.LOGGER
import idk.bluecross.fhax16.module.Module

class ModeSetting<T>(
    override val module: Module,
    override val name: String,
    private var value: T,
    private val possibles: List<T>,
    override val page: SettingPage = SettingPage.NONE
) : ISettingAbstract {

    init {
        module.settings.add(this)
    }

    fun set(at: Int) {
        if (at < possibles.size && at >= 0) {
            value = possibles[at]
        }
    }

    fun set(value: T) {
        if (possibles.contains(value)) {
            this.value = value
        }
    }

    fun value() = value
    override fun setValByString(str: String): String {
        if (str.matches(Regex("\\d+"))) { //trying to set with index
            if (str.toInt() <= possibles.size) {
                value = possibles[str.toInt() + 1]
                return "Success!"
            } else {
                return "Index is too huge! Max index is " + possibles.size
            }
        } else if (possibles.map { it.toString().lowercase() }.contains(str)) {
            val newVal = possibles.first {
                it.toString().lowercase() == str.lowercase()
            }
            value = newVal
            return "Success!"
        } else {
            return "Wrong value. All allowed values: " + possibles.toString().removeSurrounding("[", "]")
        }
    }

    override fun getValueAsString(): String {
        return value().toString()
    }

    override fun getPrettyInfo(): String {
        return name + ": " + value + " possibles: $possibles"
    }
}