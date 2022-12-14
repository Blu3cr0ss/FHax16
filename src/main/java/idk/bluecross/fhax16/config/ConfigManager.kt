package idk.bluecross.fhax16.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import idk.bluecross.fhax16.LOGGER
import idk.bluecross.fhax16.modules
import java.io.File

object ConfigManager {
    var file = File("FHax16/configs", "default.fhaxcfg")
    val binds = File("FHax16", "keybinds.txt")
    val mapper = ObjectMapper()
    val jackson = mapper.setVisibility(
        mapper.serializationConfig.defaultVisibilityChecker
            .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
    )
        .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .enable(SerializationFeature.INDENT_OUTPUT)

    init {
        file.createIfNotExist()
        binds.createIfNotExist()
    }

    fun getBinds() {
        try {
            binds.readLines().forEach {
                modules.firstOrNull { q -> it.split(": ")[0] == q.name }?.let { m ->
                    m.setBind(it.split(": ")[1].trim().toInt())
                }
            }
        } catch (e: Exception) {
            LOGGER.error("error loading binds")
            LOGGER.warn("Trying to create new cfg")
            try{
                ConfigManager.saveCfg()
            }catch(e2:Exception){
                LOGGER.error("Bruh, we failed again. Go submit bug report to devs")
            }
        }
    }

    fun saveBinds() {
        try {
            val list = modules
            val builder = StringBuilder()
            list.forEach {
                builder.append(it.name + ": " + it.key + "\n")
            }
            binds.writeText(builder.toString())
        } catch (e: Exception) {
            LOGGER.error("error saving binds")
        }
    }

    fun saveCfg(file: File = this.file) {
        try {
            jackson.writeValue(file, modules)
            LOGGER.info("saved cfg")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCfg(file: File = this.file): Array<Map<String, HashMap<String, String>>>? {
        try {
            val tobe = arrayOf<Map<String, HashMap<String, String>>>()
            val result = jackson.readValue(file, tobe::class.java)
            result.forEach {
                it.forEach {
                    val realModule = modules.firstOrNull { m -> m.name == it.key }?.let { realModule ->
                        val settings = it.value
                        if (settings["enabled"].toBoolean()) realModule.enable() else realModule.disable()
                        settings.remove("enabled")
                        settings.forEach { q ->
                            val setting = realModule.settings.firstOrNull {
                                it.name == q.key
                            } ?: return@forEach
                            setting.setValByString(q.value)
                        }
                    }
                }
            }
            LOGGER.info("loaded cfg")
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            LOGGER.error("error loading binds")
            LOGGER.warn("Trying to create new binds file")
            try{
                ConfigManager.saveCfg()
            }catch(e2:Exception){
                LOGGER.error("Bruh, we failed again. Go submit bug report to devs")
            }
        }
        return arrayOf()
    }

    fun File.createIfNotExist(): File {
        val file = this
        try {
            if (!file.parentFile.exists()) file.parentFile.mkdirs()
            if (!file.exists()) file.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }
}
