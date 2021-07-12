package com.github.donghune.rating.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("CreditRatingConfig")
data class CreditRatingConfig (
    val creditRatingList: List<CreditRating>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): CreditRatingConfig {
            return CreditRatingConfig(
                data["creditRatingList"] as List<CreditRating>,
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "creditRatingList" to creditRatingList,
        )
    }

}