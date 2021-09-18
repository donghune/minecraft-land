package com.github.donghune.land.model.usecase

import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import org.bukkit.Bukkit

data class GroupDeleteUseCaseParam(
    val group: Group,
)

object GroupDeleteUseCase : BaseUseCase<GroupDeleteUseCaseParam, Unit>() {
    override fun validation(param: GroupDeleteUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: GroupDeleteUseCaseParam) {

        param.group.getLandList().forEach { land ->
            SellLandUseCaseParam(land).run {
                SellLandUseCase.execute(this@run)
            }
        }

        when (param.group) {
            is Village -> {
                VillageRepository.remove(param.group.uuid)
            }
            is Nation -> {
                NationRepository.remove(param.group.uuid)
            }
        }

        Bukkit.broadcastMessage("${param.group.name} ${param.group.getType().korName}이 삭제되었습니다.")
    }
}
