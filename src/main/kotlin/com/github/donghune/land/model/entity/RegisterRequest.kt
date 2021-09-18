package com.github.donghune.land.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("RegisterRequest")
data class RegisterRequest(
    val requesterUUID: String,
    val groupUUID: String,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "requesterUUID" to requesterUUID,
            "groupUUID" to groupUUID,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): RegisterRequest {
            return RegisterRequest(
                data["requesterUUID"] as String,
                data["groupUUID"] as String
            )
        }
    }
}
