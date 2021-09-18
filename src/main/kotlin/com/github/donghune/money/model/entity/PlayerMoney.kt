package com.github.donghune.money.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("PlayerMoney")
data class PlayerMoney(
    val uuid: String,
    var money: Long
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): PlayerMoney {
            return PlayerMoney(
                data["uuid"] as String,
                data["money"].toString().toLong()
            )
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "uuid" to uuid,
            "money" to money
        )
    }

    fun giveMoney(money: Long) {
        this.money += money
    }

    fun takeMoney(money: Long) {
        this.money -= money
    }
}
