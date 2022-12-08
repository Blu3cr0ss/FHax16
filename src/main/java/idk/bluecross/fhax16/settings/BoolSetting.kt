package idk.bluecross.fhax16.settings

import idk.bluecross.fhax16.module.Module

class BoolSetting(
    override val module: Module,
    override val name: String,
    private var value: Boolean,
    override val page: SettingPage = SettingPage.NONE,
) :
    ISettingAbstract {
    init {
        module.settings.add(this)
    }

    fun toggle() {
        value = !value
    }

    fun value() = value
    override fun setValByString(str: String): String {
        value = when (str) {
            "true" -> true
            "false" -> false
            else -> return "Wrong setting value. Valid: true/false"
        }
        return "Success!"
    }

    override fun getValueAsString(): String {
        return value().toString()
    }

    override fun getPrettyInfo(): String {
        return name + ": " + value
    }
}