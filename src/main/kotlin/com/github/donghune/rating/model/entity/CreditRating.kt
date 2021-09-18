package com.github.donghune.rating.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("CreditRating")
data class CreditRating(
    val id: Int,
    val rating: Int,
    val tag: String,
    val price: Int,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): CreditRating {
            return CreditRating(
                data["id"] as Int,
                data["rating"] as Int,
                data["tag"] as String,
                data["price"] as Int,
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "rating" to rating,
            "tag" to tag,
            "price" to price,
        )
    }
}
