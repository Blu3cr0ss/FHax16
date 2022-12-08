package idk.bluecross.fhax16.settings

import idk.bluecross.fhax16.module.Module

class IntSetting(
    override val module: Module,
    override val name: String,
    private var value: Int,
    val min: Int,
    val max: Int,
    override val page: SettingPage = SettingPage.NONE
) : ISettingAbstract {
    init {
        module.settings.add(this)
    }

    fun set(value: Int) {
        if (value < min) {
            this.value = min
        } else if (value > max) {
            this.value = max
        } else {
            this.value = value
        }
    }

    fun value() = value
    override fun setValByString(str: String): String {
        if (Regex("^(\\d+)$").matches(str)) {   // only numbers or nums and one dot
            set(str.toInt())
            return "Success!"
        } else {
            return "Wrong value. Examples: 5/1/8. Min/Max: $min/$max"
        }
    }

    override fun getValueAsString(): String {
        return value().toString()
    }
    override fun getPrettyInfo(): String {
        return name+": "+value+" min: $min max: $max"
    }
}