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

data class GroupChildJoinDismissUseCaseParam(
    val group: Group,
    val childUUID: UUID,
)

object GroupChildJoinDismissUseCase : BaseUseCase<GroupChildJoinDismissUseCaseParam, Unit>() {
    override fun validation(param: GroupChildJoinDismissUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: GroupChildJoinDismissUseCaseParam) {
        RegisterRequestRepository.decline(param.childUUID)
        when (param.group) {
            is Village -> param.childUUID.toPlayer()?.sendInfoMessage("요청이 거절되었습니다.")
            is Nation -> param.childUUID.getVillage()?.getOwnerPlayer()?.sendInfoMessage("요청이 거절되었습니다.")
        }
    }
}