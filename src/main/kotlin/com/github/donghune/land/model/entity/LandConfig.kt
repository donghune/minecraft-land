package com.github.donghune.land.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("LandConfig")
data class LandConfig(
    val personalLandBuyPrice: Int,
    val personalLandSellPrice: Int,
    val villageLandBuyPrice: Int,
    val villageLandSellPrice: Int,
    val villageBuildPrice: Int,
    val nationLandBuyPrice: Int,
    val nationLandSellPrice: Int,
    val nationBuildPrice: Int,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "personalLandBuyPrice" to personalLandBuyPrice,
            "personalLandSellPrice" to personalLandSellPrice,
            "villageLandBuyPrice" to villageLandBuyPrice,
            "villageLandSellPrice" to villageLandSellPrice,
            "villageBuildPrice" to villageBuildPrice,
            "nationLandBuyPrice" to nationLandBuyPrice,
            "nationLandSellPrice" to nationLandSellPrice,
            "nationBuildPrice" to nationBuildPrice,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): LandConfig {
            return LandConfig(
                data["personalLandBuyPrice"] as Int,
                data["personalLandSellPrice"] as Int,
                data["villageLandBuyPrice"] as Int,
                data["villageLandSellPrice"] as Int,
                data["villageBuildPrice"] as Int,
                data["nationLandBuyPrice"] as Int,
                data["nationLandSellPrice"] as Int,
                data["nationBuildPrice"] as Int,
            )
        }
    }
}
