package com.github.donghune.land.model.land.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("Nation")
data class Nation (
    val uuid: String,
    val name: String,
    val owner: String,
    val villages: List<String>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Nation {
            return Nation(
                data["uuid"] as String,
                data["name"] as String,
                data["owner"] as String,
                data["villages"] as List<String>,
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid,
            "name" to name,
            "owner" to owner,
            "villages" to villages,
        )
    }

}