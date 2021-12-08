package com.github.donghune.regenblock.model

import com.github.donghune.deprecated.NBoxRepository
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.util.BoundingBox

@SerializableAs("RegenRegion")
data class RegenRegion(
    val name: String,
    val world: World,
    val area: BoundingBox,
    val regenRegionInfo: RegenRegionInfo,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): RegenRegion {
            return RegenRegion(
                data["name"] as String,
                try {
                    Bukkit.getWorld(data["world"] as String)!!
                } catch (exception: Exception) {
                    NBoxRepository.get(data["area"] as String)!!.pos1.world
                },
                try {
                    data["area"] as BoundingBox
                } catch (exception: Exception) {
                    NBoxRepository.get(data["area"] as String)!!.toBoundingBox()
                },
                RegenRegionInfoRepository.get(data["regenRegionInfo"] as String)!!
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "world" to world.name,
            "area" to area,
            "regenRegionInfo" to regenRegionInfo.name
        )
    }
}
