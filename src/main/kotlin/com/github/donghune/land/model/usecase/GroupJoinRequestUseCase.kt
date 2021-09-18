package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.repository.RegisterRequestRepository
import java.util.*

data class GroupJoinRequestUseCaseParam(
    val group: Group,
    val childUUID: UUID,
)

object GroupJoinRequestUseCase : BaseUseCase<GroupJoinRequestUseCaseParam, Unit>() {
    override fun validation(param: GroupJoinRequestUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: GroupJoinRequestUseCaseParam) {
        RegisterRequestRepository.request(param.childUUID, param.group.uuid.toUUID())
    }
}
