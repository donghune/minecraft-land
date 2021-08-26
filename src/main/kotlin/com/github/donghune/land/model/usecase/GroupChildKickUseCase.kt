package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.getVillage
import com.github.donghune.land.extension.sendInfoMessage
import com.github.donghune.land.extension.toPlayer
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.namulibrary.extension.sendInfoMessage
import java.util.*

data class GroupChildKickUseCaseParam(
    val group: Group,
    val childUUID: UUID,
)

object GroupChildKickUseCase : BaseUseCase<GroupChildKickUseCaseParam, Unit>() {
    override fun validation(param: GroupChildKickUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: GroupChildKickUseCaseParam) {
        param.group.member.remove(param.childUUID.toString())
        when (param.group) {
            is Village -> param.childUUID.toPlayer()?.sendInfoMessage("마을에서 추방되었습니다.")
            is Nation -> param.childUUID.getVillage()?.getOwnerPlayer()?.sendInfoMessage("국가에서 추방되었습니다.")
        }
    }
}