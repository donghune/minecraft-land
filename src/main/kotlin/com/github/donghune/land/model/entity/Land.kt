package com.github.donghune.land.model.entity

import com.github.donghune.namulibrary.extension.ItemBuilder
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack
import org.bukkit.util.BoundingBox
import java.util.*

@SerializableAs("Land")
data class Land(
    val name: String,
    val boundingBox: BoundingBox,
    var type: LandType,
    var owner: String,
    val member: MutableList<UUID>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Land {
            return Land(
                data["name"] as String,
                data["boundingBox"] as BoundingBox,
                LandType.valueOf(data["type"] as String),
                data["owner"] as String,
                (data["member"] as List<String>).map { UUID.fromString(it) }.toMutableList(),
            )
        }
    }

    override fun serialize(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "boundingBox" to boundingBox,
            "type" to type.toString(),
            "owner" to owner,
            "member" to member.map { it.toString() }.toList(),
        )
    }

    fun toItemStack(): ItemStack {
        return ItemBuilder()
            .setMaterial(Material.GRASS_BLOCK)
            .setDisplay("&f${name} 토지")
            .setLore(
                listOf(
                    "위치 X[%d] Z[%d]".format(boundingBox.centerX.toInt(), boundingBox.centerZ.toInt()),
                )
            )
            .build()
    }

}