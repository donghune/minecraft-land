package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.hasMoney
import com.github.donghune.land.extension.sendErrorMessage
import com.github.donghune.land.model.entity.Group
import org.bukkit.entity.Player

data class GroupVaultDepositUseCaseParam(
    val player: Player,
    val amount: Int,
    val group: Group,
) : UseCaseParam()

object GroupVaultDepositUseCase : BaseUseCase<GroupVaultDepositUseCaseParam, Unit>() {

    override fun validation(param: GroupVaultDepositUseCaseParam): Boolean {
        val player = param.player
        val amount = param.amount
        val group = param.group

        // 돈을 가지고 있는가?
        if (!player.hasMoney(amount)) {
            player.sendErrorMessage("보유중인 금액이 부족합니다.")
            return false
        }

        // 금고가 가득차진 않는가?
        amount

        // 고러면 입금 하세요!!
        return true
    }

    override fun execute(param: GroupVaultDepositUseCaseParam) {
    }
}
