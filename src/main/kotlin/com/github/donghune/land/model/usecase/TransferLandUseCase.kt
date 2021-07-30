package com.github.donghune.land.model.usecase

import org.bukkit.entity.Player

data class TransferLandUseCaseParam(
    val player : Player
)

object TransferLandUseCase : BaseUseCase<TransferLandUseCaseParam, Unit>() {
    override fun validation(param: TransferLandUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: TransferLandUseCaseParam) {
    }
}