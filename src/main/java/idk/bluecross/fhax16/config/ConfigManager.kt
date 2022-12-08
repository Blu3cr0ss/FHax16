package idk.bluecross.fhax16.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.modules
import java.io.File

object ConfigManager {
    var file = File("FHax16/configs", "default.fhaxcfg")
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
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        if (!file.exists()) file.createNewFile()
    }

    fun saveCfg() {
        jackson.writeValue(file, modules)
    }

    fun getCfg(): List<*>? {
        val result = jackson.readValue(file, List::class.java)
        return result
    }
}