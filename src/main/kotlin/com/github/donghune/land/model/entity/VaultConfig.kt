package com.github.donghune.land.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("VaultConfig")
data class VaultConfig(
    val villageVault: String,
    val nationVault: String,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "villageVault" to villageVault,
            "nationVault" to nationVault,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): VaultConfig {
            return VaultConfig(
                data["villageVault"] as String,
                data["nationVault"] as String,
            )
        }
    }
}
