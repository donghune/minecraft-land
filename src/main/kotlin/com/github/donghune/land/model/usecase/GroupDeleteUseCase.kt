package com.github.donghune.land.model.usecase

import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.VillageRepository

data class GroupDeleteUseCaseParam(
    val group : Group,
)

object GroupDeleteUseCase : BaseUseCase<GroupDeleteUseCaseParam, Unit>() {
    override fun validation(param: GroupDeleteUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: GroupDeleteUseCaseParam) {
        when(param.group) {
            is Village -> {
                VillageRepository.remove(param.group.uuid)
                LandRepository.getList()
                    .filter { it.owner == param.group.uuid }
                    .forEach { LandRepository.remove(it.chunkKey.toString()) }
            }
            is Nation -> {

            }
        }
    }
}