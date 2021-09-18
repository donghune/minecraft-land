package com.github.donghune.land.model.entity

import com.github.donghune.land.model.repository.LandConfigRepository

enum class LandType(
    val korName: String,
) {
    NONE("기본"),
    PERSONAL("개인"),
    VILLAGE("마을"),
    NATION("국가");

    fun getLandBuyPrice(): Long {
        return when (this) {
            PERSONAL -> 1792800
            VILLAGE -> 2988000
            NATION -> 5976000
            else -> return 0
        }
    }

    fun getBuildVaultPrice(): Int {
        return when (this) {
            VILLAGE -> LandConfigRepository.get().villageBuildVaultPrice
            NATION -> LandConfigRepository.get().nationBuildVaultPrice
            else -> 0
        }
    }

    fun getBuildLandPrice(): Int {
        return when (this) {
            VILLAGE -> LandConfigRepository.get().villageBuildLandPrice
            NATION -> LandConfigRepository.get().nationBuildLandPrice
            else -> 0
        }
    }

    fun getBuildBuildingPrice(): Int {
        return when (this) {
            VILLAGE -> LandConfigRepository.get().villageBuildBuildingPrice
            NATION -> LandConfigRepository.get().nationBuildBuildingPrice
            else -> 0
        }
    }


    fun getBuildPrice(): Int {
        return when (this) {
            VILLAGE -> LandConfigRepository.get().villageBuildPrice
            NATION -> LandConfigRepository.get().nationBuildPrice
            else -> 0
        }
    }

}
