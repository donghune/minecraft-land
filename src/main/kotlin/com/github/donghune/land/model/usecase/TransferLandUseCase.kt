package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.*
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import org.bukkit.entity.Player

data class TransferLandUseCaseParam(
    val player: Player,
    val land: Land,
    val target: Player,
)

object TransferLandUseCase : BaseUseCase<TransferLandUseCaseParam, Unit>() {
    override fun validation(param: TransferLandUseCaseParam): Boolean {
        // 양도 하려는 땅이 개인 일 경우
        return when (param.land.type) {
            LandType.PERSONAL -> {
                // 상대방이 토지가 모두 가득 차 있을 경우
                if (param.target.isHasMoreLand(LandType.PERSONAL)) {
                    param.player.sendErrorMessage("상대방의 토지 개수가 이미 가득 차 있습니다.")
                    return false
                }

                return true
            }
            LandType.VILLAGE -> {
                // 그룹 일 경우 해당 그룹의 그룹장에게만 넘겨줄 수 있어야 함
                val player = param.player
                val target = param.target
                val playerVillage = player.getBelongingVillage()
                val targetVillage = target.getBelongingVillage()

                if (playerVillage == null || playerVillage.owner != player.uniqueId.toString()) {
                    player.sendErrorMessage("마을에 속해있지 않거나 마을 장이 아닙니다.")
                }

                if (targetVillage == null || targetVillage.owner != target.uniqueId.toString()) {
                    player.sendErrorMessage("상대방이 마을에 속해있지 않거나 마을 장이 아닙니다.")
                    return false
                }

                if (target.isHasMoreLand(LandType.VILLAGE)) {
                    player.sendErrorMessage("상대방의 마을 토지 개수가 이미 가득 차 있습니다.")
                    return false
                }

                return true
            }
            LandType.NATION -> {
                // 그룹 일 경우 해당 그룹의 그룹장에게만 넘겨줄 수 있어야 함
                val player = param.player
                val target = param.target
                val playerNation = player.getBelongingNation()
                val targetNation = target.getBelongingNation()

                if (playerNation == null || playerNation.owner != player.uniqueId.toString()) {
                    player.sendErrorMessage("국가에 속해있지 않거나 국가 장이 아닙니다.")
                }

                if (targetNation == null || targetNation.owner != target.uniqueId.toString()) {
                    player.sendErrorMessage("상대방이 국가에 속해있지 않거나 국가 장이 아닙니다.")
                    return false
                }

                if (target.isHasMoreLand(LandType.NATION)) {
                    player.sendErrorMessage("상대방의 국가 토지 개수가 이미 가득 차 있습니다.")
                    return false
                }

                return true
            }
            else -> false
        }
    }

    override fun execute(param: TransferLandUseCaseParam) {
        when (param.land.type) {
            LandType.PERSONAL -> {
                // 상대방이 토지가 모두 가득 차 있을 경우
                param.land.owner = param.target.uniqueId.toString()
            }
            LandType.VILLAGE -> {
                val targetVillage = param.target.getBelongingVillage()
                param.land.owner = targetVillage!!.uuid
            }
            LandType.NATION -> {
                val targetVillage = param.target.getBelongingNation()
                param.land.owner = targetVillage!!.uuid
            }
            else -> {
            }
        }
        param.player.sendInfoMessage("토지를 양도하였습니다.")
    }
}
