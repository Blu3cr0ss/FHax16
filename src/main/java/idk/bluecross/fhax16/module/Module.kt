package idk.bluecross.fhax16.module

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import idk.bluecross.fhax16.keyboard.KeyBind
import idk.bluecross.fhax16.modules
import idk.bluecross.fhax16.settings.ISettingAbstract
import net.minecraftforge.common.MinecraftForge
import org.lwjgl.glfw.GLFW
import java.util.stream.Collectors

//@JsonIgnoreProperties(ignoreUnknown = true)
abstract class Module(
    @JsonProperty("name") val name: String,
    val description: String,
    val key: Int = GLFW.GLFW_KEY_UNKNOWN
) {
    @JsonIgnore
    private var isEnabled = false
    private var keybind: KeyBind
    lateinit var category: Category
    val settings = arrayListOf<ISettingAbstract>()

    init {
        keybind = KeyBind(key, ::toggle)
        modules.add(this)
    }

    private fun getKeyBindAsString(): String {
        return GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key))!!
    }

    @JsonAnyGetter
    private fun collectSettingsToJson(): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["enabled"] = "false"
        map["key"] = getKeyBindAsString()
        map.putAll(
            settings.stream().collect(Collectors.toMap(ISettingAbstract::name, ISettingAbstract::getValueAsString))
        );
        return map
    }

    fun isEnabled() = isEnabled

    fun enable() {
        isEnabled = true
        onEnableAndReg()
    }

    fun disable() {
        isEnabled = false
        onDisableAndReg()
    }

    fun toggle() {
        if (isEnabled) disable() else enable()
    }

    private fun onEnableAndReg() {      // i made it for safety, so now i dont need to do super() for overriding fun. you should override normal onEnable
        MinecraftForge.EVENT_BUS.register(this)
        onEnable()
    }

    protected open fun onEnable() {

    }

    private fun onDisableAndReg() {
        onDisable()
        MinecraftForge.EVENT_BUS.unregister(this)
    }

    protected open fun onDisable() {

    }

    enum class Category {
        MISC,
        PVP,
        MOVEMENT,
        PLAYER,
        CLIENT
    }

    fun getCategoryByString(str: String): Category {
        return when (str.uppercase()) {
            "MISC" -> Category.MISC
            "PVP" -> Category.PVP
            "MOVEMENT" -> Category.MOVEMENT
            "PLAYER" -> Category.PLAYER
            "CLIENT" -> Category.CLIENT
            else -> Category.CLIENT
        }
    }
}