package com.github.donghune.land.model.credit_rating.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("PlayerCreditRating")
data class PlayerCreditRating (
    val uuid: UUID,
    var ratingId: Int,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): PlayerCreditRating {
            return PlayerCreditRating(
                UUID.fromString(data["uuid"] as String),
                data["ratingId"] as Int,
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid.toString(),
            "ratingId" to ratingId,
        )
    }

}