package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.hasMoney
import com.github.donghune.land.extension.takeMoney
import com.github.donghune.land.model.config.pref
import com.github.donghune.land.model.entity.*
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import org.bukkit.entity.Player
import java.util.*

data class GroupBuildUseCaseParam(
    val player: Player,
    val landType: LandType,
)

object GroupBuildUseCase : BaseUseCase<GroupBuildUseCaseParam, Unit>() {
    override fun validation(param: GroupBuildUseCaseParam): Boolean {
        if (LandRepository.get(param.player.chunk.chunkKey.toString()) != null) {
            param.player.sendErrorMessage("이미 누가 소유하고 있는 토지입니다.")
            return false
        }

        val buildPrice = when (param.landType) {
            LandType.VILLAGE -> pref.villageBuildPrice
            LandType.NATION -> pref.nationBuildPrice
            else -> 0
        }

        if (param.player.hasMoney() < buildPrice) {
            param.player.sendErrorMessage("보유하고 있는 금액이 부족합니다.")
            return false
        }
        return true
    }

    override fun execute(param: GroupBuildUseCaseParam) {
        Land(
            param.player.chunk.chunkKey,
            param.landType,
            param.player.uniqueId.toString(),
            mutableListOf(),
            LandOption.getDefaultTable()
        ).also { LandRepository.create(it.chunkKey.toString(), it) }
        when (param.landType) {
            LandType.VILLAGE -> {
                Village(
                    UUID.randomUUID().toString(),
                    param.player.uniqueId.toString(),
                    param.player.uniqueId.toString(),
                    mutableListOf(),
                    0,
                    0
                ).also { VillageRepository.create(it.uuid, it) }
            }
            LandType.NATION -> {
                Nation(
                    UUID.randomUUID().toString(),
                    param.player.uniqueId.toString(),
                    param.player.uniqueId.toString(),
                    mutableListOf(),
                    0,
                    0
                ).also { NationRepository.create(it.uuid, it) }
            }
            else -> return
        }

        param.player.takeMoney(pref.villageBuildPrice.toLong())
        param.player.sendInfoMessage("${param.landType.korName}을 건설하였습니다.")
    }

}