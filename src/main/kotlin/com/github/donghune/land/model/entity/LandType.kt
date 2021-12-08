package com.github.donghune.land.model.entity

import com.github.donghune.land.model.config.pref

enum class LandType(
    val korName: String,
) {
    NONE("기본"),
    PERSONAL("개인"),
    VILLAGE("마을"),
    NATION("국가");

    fun getLandBuyPrice(): Int {
        return when (this) {
            PERSONAL -> pref.personalLandBuyPrice
            VILLAGE -> pref.villageLandBuyPrice
            NATION -> pref.nationLandBuyPrice
            else -> return 0
        }
    }

    fun getBuildVaultPrice(): Int {
        return when (this) {
            VILLAGE -> pref.villageBuildVaultPrice
            NATION -> pref.nationBuildVaultPrice
            else -> 0
        }
    }

    fun getBuildLandPrice(): Int {
        return when (this) {
            VILLAGE -> pref.villageBuildLandPrice
            NATION -> pref.nationBuildLandPrice
            else -> 0
        }
    }

    fun getBuildBuildingPrice(): Int {
        return when (this) {
            VILLAGE -> pref.villageBuildBuildingPrice
            NATION -> pref.nationBuildBuildingPrice
            else -> 0
        }
    }


    fun getBuildPrice(): Int {
        return when (this) {
            VILLAGE -> pref.villageTotalPrice
            NATION -> pref.nationTotalPrice
            else -> 0
        }
    }

}
