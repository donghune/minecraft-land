package com.github.donghune.land.model.usecase

import org.bukkit.entity.Player

data class UpgradeVaultUseCaseParam(
    val player: Player
)

object UpgradeVaultUseCase : BaseUseCase<UpgradeVaultUseCaseParam, Unit>() {
    override fun validation(param: UpgradeVaultUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: UpgradeVaultUseCaseParam) {
    }
}
