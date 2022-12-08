package idk.bluecross.fhax16.settings

import idk.bluecross.fhax16.module.Module

interface ISettingAbstract {
    val module: Module
    val name: String
    val page: SettingPage
    fun setValByString(str: String): String
    fun getValueAsString(): String
    fun getPrettyInfo():String
}