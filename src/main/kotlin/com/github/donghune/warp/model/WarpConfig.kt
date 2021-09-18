package com.github.donghune.warp.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("WarpConfig")
data class WarpConfig(
    val warpPointList: List<WarpPoint>
) : ConfigurationSerializable {
    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): WarpConfig {
            return WarpConfig(
                data["warpPointList"] as List<WarpPoint>
            )
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "warpPointList" to warpPointList
        )
    }
}
