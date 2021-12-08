package com.github.donghune.shop.model.entity

import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.namulibrary.extension.toMoneyFormat
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack

@SerializableAs("Stuff")
data class Stuff(
    val itemStack: ItemStack,
    val buyPrice: Int,
    val sellPrice: Int,
) : ConfigurationSerializable, Cloneable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Stuff {
            return Stuff(
                data["itemStack"] as ItemStack,
                data["buyPrice"] as Int,
                data["sellPrice"] as Int
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "itemStack" to itemStack,
            "buyPrice" to buyPrice,
            "sellPrice" to sellPrice
        )
    }

    fun toItemStack(): ItemStack {
        val itemStack = itemStack.clone()
        val itemMeta = itemStack.itemMeta

        val lore = mutableListOf<String>(*(itemMeta?.lore ?: listOf<String>()).toTypedArray())
        lore.add("")

        if (buyPrice < 0) {
            lore.add("&f구매가격 : &c구매불가".replaceChatColorCode())
        } else {
            lore.add("&f구매가격 : ${buyPrice.toMoneyFormat()}".replaceChatColorCode())
        }

        if (sellPrice < 0) {
            lore.add("&f판매가격 : &c판매불가".replaceChatColorCode())
        } else {
            lore.add("&f판매가격 : ${sellPrice.toMoneyFormat()}".replaceChatColorCode())
        }

        itemMeta?.lore = lore
        itemStack.itemMeta = itemMeta

        return itemStack
    }

    override fun clone(): Any {
        return super.clone()
    }
}
