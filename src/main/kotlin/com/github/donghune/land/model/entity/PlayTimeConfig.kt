package com.github.donghune.land.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("PlaytimeConfig")
data class PlayTimeConfig(
    var server: Int,
    val players: MutableMap<UUID, Int>,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "server" to server,
            "players" to players.mapKeys { it.key.toString() },
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): PlayTimeConfig {
            return PlayTimeConfig(
                data["server"] as Int,
                (data["players"] as MutableMap<String, Int>).mapKeys { UUID.fromString(it.key) }.toMutableMap(),
            )
        }
    }
}
