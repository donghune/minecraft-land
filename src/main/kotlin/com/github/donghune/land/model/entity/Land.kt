package com.github.donghune.land.model.entity

import com.github.donghune.land.extension.getChunk
import com.github.donghune.namulibrary.extension.ItemBuilder
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
    var landOption: MutableMap<LandOption, Boolean>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Land {
            return Land(
                data["chunkKey"] as Long,
                LandType.valueOf(data["type"] as String),
                data["owner"] as String,
                (data["member"] as List<String>).map { UUID.fromString(it) }.toMutableList(),
                (data["landOption"] as Map<String, Boolean>)
                    .mapKeys { LandOption.valueOf(it.key) }
                    .toMap() as MutableMap<LandOption, Boolean>
            )
        }
    }

    override fun serialize(): Map<String, Any?> {
        return mapOf(
            "chunkKey" to chunkKey,
            "type" to type.toString(),
            "owner" to owner,
            "member" to member.map { it.toString() }.toList(),
            "environmentPermission" to landOption.mapKeys { it.key.toString() },
        )
    }

    fun toItemStack(index: Int): ItemStack {
        val chunk = getChunk()
        return ItemBuilder()
            .setMaterial(Material.GRASS_BLOCK)
            .setDisplay("&f${index}번 토지")
            .setLore(
                listOf(
                    "위치 X[%d] Z[%d]".format(chunk.x, chunk.z),
                )
            )
            .build()
    }

}