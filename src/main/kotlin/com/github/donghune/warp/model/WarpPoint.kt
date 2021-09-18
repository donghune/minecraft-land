package com.github.donghune.warp.model

import com.github.donghune.namulibrary.nms.addNBTTagCompound
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack

@SerializableAs("WarpPoint")
data class WarpPoint(
    val name: String,
    val location: Location,
    val iconMaterial: Material,
    val iconDisplay: String,
    val iconLore: List<String>
) : ConfigurationSerializable {
    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): WarpPoint {
            return WarpPoint(
                data["name"] as String,
                data["location"] as Location,
                Material.valueOf(data["iconMaterial"] as String),
                data["iconDisplay"] as String,
                data["iconLore"] as List<String>
            )
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "name" to name,
            "location" to location,
            "iconMaterial" to iconMaterial.name,
            "iconDisplay" to iconDisplay,
            "iconLore" to iconLore
        )
    }

    class MetaData(
        val name: String
    )

    fun toItemStack(): ItemStack {
        return ItemStackFactory()
            .setType(iconMaterial)
            .setDisplayName(iconDisplay)
            .setLore(iconLore)
            .build()
            .addNBTTagCompound(MetaData(name))
    }
}
