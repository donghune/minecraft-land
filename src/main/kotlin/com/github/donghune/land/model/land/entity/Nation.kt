package com.github.donghune.land.model.land.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("Nation")
data class Nation (
    val uuid: UUID,
    val name: String,
    val owner: UUID,
    val villages: List<UUID>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Nation {
            return Nation(
                data["uuid"] as UUID,
                data["name"] as String,
                data["owner"] as UUID,
                data["villages"] as List<UUID>,
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