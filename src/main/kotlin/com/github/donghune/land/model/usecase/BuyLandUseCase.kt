package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.*
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.entity.LandOption
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import org.bukkit.entity.Player

data class BuyLandUseCaseParam(
    val player: Player,
    val landType: LandType
) : UseCaseParam()

object BuyLandUseCase : BaseUseCase<BuyLandUseCaseParam, Unit>() {

    override fun validation(param: BuyLandUseCaseParam): Boolean {
        if (LandRepository.get(param.player.chunk.chunkKey.toString()) != null) {
            param.player.sendErrorMessage("이미 누가 소유하고 있는 토지입니다.")
            return false
        }

        if (param.player.getBelongingVillage() == null || param.player.getBelongingNation() == null) {
            param.player.sendErrorMessage("소유하고 있는 마을 또는 국가가 존재하지 않습니다.")
            return false
        }

        if (param.player.hasMoney() < param.landType.getLandBuyPrice()) {
            param.player.sendErrorMessage("소지금액이 부족합니다.")
            return false
        }

        if (!param.player.isHasMoreLand(param.landType)) {
            param.player.sendErrorMessage("구매 가능한 땅의 갯수를 초과 하였습니다.")
            return false
        }

        return true
    }

    override fun execute(param: BuyLandUseCaseParam) {
        Land(
            param.player.chunk.chunkKey,
            LandType.PERSONAL,
            param.player.uniqueId.toString(),
            mutableListOf(),
            LandOption.getDefaultTable()
        ).also { LandRepository.create(it.chunkKey.toString(), it) }
        param.player.takeMoney(param.landType.getLandBuyPrice())
        param.player.sendInfoMessage("토지를 구매하였습니다.")
    }

}