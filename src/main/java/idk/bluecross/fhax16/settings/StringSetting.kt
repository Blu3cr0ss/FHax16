package idk.bluecross.fhax16.settings

import idk.bluecross.fhax16.module.Module

class StringSetting(
    override val module: Module,
    override val name: String,
    private var value: String,
    override val page: SettingPage = SettingPage.NONE
) : ISettingAbstract {

    fun value(): String {
        return value
    }

    override fun setValByString(str: String): String {
        value = str
        return "Success!"
    }

    fun set(value: String){
        this.value = value
    }

    override fun getValueAsString(): String {
        return value
    }

    override fun getPrettyInfo(): String {
        return "$name: $value"
    }

}