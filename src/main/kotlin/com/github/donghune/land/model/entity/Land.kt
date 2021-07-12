package com.github.donghune.land.model.entity

import com.github.donghune.namulibrary.extension.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack
import java.util.*

@SerializableAs("Land")
data class Land(
    val chunkKey: Long,
    var type: LandType,
    var owner: String,
    val member: MutableList<UUID>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Land {
            return Land(
                data["chunkKey"] as Long,
                LandType.valueOf(data["type"] as String),
                data["owner"] as String,
                (data["member"] as List<String>).map { UUID.fromString(it) }.toMutableList(),
            )
        }
    }

    override fun serialize(): Map<String, Any?> {
        return mapOf(
            "chunkKey" to chunkKey,
            "type" to type.toString(),
            "owner" to owner,
            "member" to member.map { it.toString() }.toList(),
        )
    }

    fun getChunk() : Chunk = Bukkit.getWorld("world")!!.getChunkAt(chunkKey)

    fun toItemStack(index : Int): ItemStack {
        return ItemBuilder()
            .setMaterial(Material.GRASS_BLOCK)
            .setDisplay("&f${index}번 토지")
            .setLore(
                listOf(
                    "위치 X[%d] Z[%d]".format(getChunk().x, getChunk().z),
                )
            )
            .build()
    }

}