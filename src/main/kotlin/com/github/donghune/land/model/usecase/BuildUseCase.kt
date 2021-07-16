package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.getBelongingVillage
import com.github.donghune.land.extension.hasMoney
import com.github.donghune.land.extension.takeMoney
import com.github.donghune.land.model.config.pref
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandOption
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.namulibrary.extension.sendErrorMessage
import org.bukkit.entity.Player

data class BuildVillageUseCaseParam(
    val player: Player,
    val landType: LandType
)

class BuildVillageUseCase : BaseUseCase<BuildVillageUseCaseParam, Unit>() {
    override fun validation(param: BuildVillageUseCaseParam): Boolean {
        if (param.player.getBelongingVillage() != null) {
            param.player.sendErrorMessage("${param.landType.korName}에 속해 있거나 가입되어 있는 경우 건설이 불가능합니다.")
            return false
        }

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

    override fun execute(param: BuildVillageUseCaseParam) {
        Land(
            param.player.chunk.chunkKey,
            param.landType,
            param.player.uniqueId.toString(),
            mutableListOf(),
            LandOption.getDefaultTable()
        ).also { LandRepository.create(it.chunkKey.toString(), it) }
        param.player.takeMoney(pref.villageBuildPrice.toLong())
    }

}