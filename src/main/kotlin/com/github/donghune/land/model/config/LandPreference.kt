package com.github.donghune.land.model.config

import com.github.donghune.land.model.repository.LandConfigRepository
import org.bukkit.Location

val pref: LandPreference
    get() = LandPreference

object LandPreference {

    val chuckLocation: Location
        get() = LandConfigRepository.get().chuckLocation

    val personalLandBuyPrice: Int
        get() = LandConfigRepository.get().personalLandBuyPrice

    val personalLandSellPrice: Int
        get() = LandConfigRepository.get().personalLandSellPrice

    val villageLandBuyPrice: Int
        get() = LandConfigRepository.get().villageLandBuyPrice

    val villageLandSellPrice: Int
        get() = LandConfigRepository.get().villageLandSellPrice

    val villageTotalPrice: Int
        get() = LandConfigRepository.get().villageBuildPrice

    val nationLandBuyPrice: Int
        get() = LandConfigRepository.get().nationLandBuyPrice

    val nationLandSellPrice: Int
        get() = LandConfigRepository.get().nationLandSellPrice

    val nationTotalPrice: Int
        get() = LandConfigRepository.get().nationBuildPrice

    val villageBuildVaultPrice: Int
        get() = LandConfigRepository.get().villageBuildVaultPrice

    val villageBuildLandPrice: Int
        get() = LandConfigRepository.get().villageBuildLandPrice

    val villageBuildBuildingPrice: Int
        get() = LandConfigRepository.get().villageBuildBuildingPrice

    val nationBuildVaultPrice: Int
        get() = LandConfigRepository.get().nationBuildVaultPrice

    val nationBuildLandPrice: Int
        get() = LandConfigRepository.get().nationBuildLandPrice

    val nationBuildBuildingPrice: Int
        get() = LandConfigRepository.get().nationBuildBuildingPrice
}
