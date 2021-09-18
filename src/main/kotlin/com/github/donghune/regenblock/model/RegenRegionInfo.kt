package com.github.donghune.regenblock.model

import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("RegenBlockInfo")
data class RegenRegionInfo(
    val name: String,
    val regenDelay: Int,
    val regenBlockInfo: Map<Material, Int>
) : ConfigurationSerializable {

    private val regenBlocks: List<Material> by lazy {
        mutableListOf<Material>().apply {
            regenBlockInfo.forEach { item -> repeat(item.value) { this.add(item.key) } }
        }
    }

    fun getRandomBlock(): Material = regenBlocks.random()

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): RegenRegionInfo {
            return RegenRegionInfo(
                data["name"] as String,
                data["regenDelay"] as Int,
                (data["regenBlockList"] as Map<String, Int>).mapKeys { Material.valueOf(it.key) }
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "regenDelay" to regenDelay,
            "regenBlockList" to regenBlockInfo.mapKeys { it.key.name }
        )
    }
}
