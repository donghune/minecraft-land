package com.github.donghune.land.model.land.entity

class LandConfig(
    val landPrice: LandPriceConfig
)

data class LandPriceConfig(
    val privateBuy: Int,
    val privateSell: Int,
    val villageBuy: Int,
    val villageSell: Int,
    val nationBuy: Int,
    val nationSell: Int,
)
