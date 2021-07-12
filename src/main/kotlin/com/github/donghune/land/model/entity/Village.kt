package com.github.donghune.land.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("Village")
data class Village (
    val uuid: String,
    val name: String,
    val owner: String,
    val member: List<String>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Village {
            return Village(
                data["uuid"] as String,
                data["name"] as String,
                data["owner"] as String,
                data["member"] as List<String>,
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid,
            "name" to name,
            "owner" to owner,
            "member" to member,
        )
    }

}