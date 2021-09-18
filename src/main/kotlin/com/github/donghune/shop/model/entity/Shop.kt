package com.github.donghune.shop.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack

@SerializableAs("Shop")
data class Shop(
    val name: String,
    val stuffList: MutableList<Stuff>,
    var npcName: String
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Shop {
            return Shop(
                data["name"] as String,
                data["stuffList"] as MutableList<Stuff>,
                data["npcName"] as String
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "stuffList" to stuffList,
            "npcName" to npcName
        )
    }

    fun addStuff(itemStack: ItemStack, buyPrice: Int, sellPrice: Int) {
        stuffList.add(Stuff(itemStack, buyPrice, sellPrice))
    }

    fun removeStuff(index: Int) {
        stuffList.removeAt(index)
    }
}
