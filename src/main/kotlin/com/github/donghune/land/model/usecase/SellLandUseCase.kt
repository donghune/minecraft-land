package com.github.donghune.land.model.usecase

import org.bukkit.entity.Player

data class SellLandUseCaseParam(
    val player : Player
)

object SellLandUseCase : BaseUseCase<SellLandUseCaseParam, Unit>() {
    override fun validation(param: SellLandUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: SellLandUseCaseParam) {
    }
}