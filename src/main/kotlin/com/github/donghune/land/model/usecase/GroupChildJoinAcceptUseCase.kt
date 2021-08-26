package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.getVillage
import com.github.donghune.land.extension.sendInfoMessage
import com.github.donghune.land.extension.toPlayer
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.RegisterRequestRepository
import com.github.donghune.namulibrary.extension.sendInfoMessage
import java.util.*

data class GroupChildJoinAcceptUseCaseParam(
    val group: Group,
    val childUUID: UUID,
)

object GroupChildJoinAcceptUseCase : BaseUseCase<GroupChildJoinAcceptUseCaseParam, Unit>() {
    override fun validation(param: GroupChildJoinAcceptUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: GroupChildJoinAcceptUseCaseParam) {
        RegisterRequestRepository.accept(param.childUUID)
        param.group.member.add(param.childUUID.toString())
        when (param.group) {
            is Village -> param.childUUID.toPlayer()?.sendInfoMessage("마을에 초대되었습니다.")
            is Nation -> param.childUUID.getVillage()?.getOwnerPlayer()?.sendInfoMessage("국가에 초대되었습니다.")
        }
    }
}