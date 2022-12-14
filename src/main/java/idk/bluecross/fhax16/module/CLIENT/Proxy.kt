package idk.bluecross.fhax16.module.CLIENT

import idk.bluecross.fhax16.module.Module
import idk.bluecross.fhax16.settings.IntSetting
import idk.bluecross.fhax16.settings.ModeSetting
import idk.bluecross.fhax16.settings.StringSetting

object Proxy : Module("Proxy", "Allows you to play minecraft through proxy so you can bypass IP ban") {
    val ip = StringSetting(this, "Ip", "localhost")
    val port = IntSetting(this, "Port", 80, 0, 65536)
    val mode = ModeSetting(this, "Mode", "http", listOf("http", "https", "both"))
    override fun onEnable() {
        when (mode.value()) {
            "http" -> {
                System.setProperty("http.proxyHost", ip.value());
                System.setProperty("http.proxyPort", port.value().toString());
            }

            "https" -> {
                System.setProperty("https.proxyHost", ip.value());
                System.setProperty("https.proxyPort", port.value().toString());
            }

            "both" -> {
                System.setProperty("http.proxyHost", ip.value());
                System.setProperty("http.proxyPort", port.value().toString());
                System.setProperty("https.proxyHost", ip.value());
                System.setProperty("https.proxyPort", port.value().toString());
            }
        }
    }



    override fun onDisable() {
        System.clearProperty("http.proxyHost");
        System.clearProperty("http.proxyPort");
        System.clearProperty("https.proxyHost");
        System.clearProperty("https.proxyPort");
    }
}