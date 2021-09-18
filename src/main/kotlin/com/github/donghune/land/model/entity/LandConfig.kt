package com.github.donghune.land.model.entity

import org.bukkit.Location
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
    val chuckLocation: Location,
    val villageBuildVaultPrice: Int,
    val villageBuildLandPrice: Int,
    val villageBuildBuildingPrice: Int,
    val nationBuildVaultPrice: Int,
    val nationBuildLandPrice: Int,
    val nationBuildBuildingPrice: Int,
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
            "chuckLocation" to chuckLocation,
            "villageBuildVaultPrice" to villageBuildVaultPrice,
            "villageBuildLandPrice" to villageBuildLandPrice,
            "villageBuildBuildingPrice" to villageBuildBuildingPrice,
            "nationBuildVaultPrice" to nationBuildVaultPrice,
            "nationBuildLandPrice" to nationBuildLandPrice,
            "nationBuildBuildingPrice" to nationBuildBuildingPrice,
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
                data["chuckLocation"] as Location,
                data["villageBuildVaultPrice"] as Int,
                data["villageBuildLandPrice"] as Int,
                data["villageBuildBuildingPrice"] as Int,
                data["nationBuildVaultPrice"] as Int,
                data["nationBuildLandPrice"] as Int,
                data["nationBuildBuildingPrice"] as Int,
            )
        }
    }
}
