package com.github.donghune.land.model.usecase

import org.bukkit.entity.Player

data class GroupDeleteUseCaseParam(
    val player : Player
)

object GroupDeleteUseCase : BaseUseCase<GroupDeleteUseCaseParam, Unit>() {
    override fun validation(param: GroupDeleteUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: GroupDeleteUseCaseParam) {
    }
}