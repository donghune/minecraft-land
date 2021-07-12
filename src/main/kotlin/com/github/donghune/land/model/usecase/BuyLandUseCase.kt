package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.hasMoney
import com.github.donghune.land.extension.takeMoney
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.permission.EnvironmentPermission
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import org.bukkit.entity.Player

object BuyLandUseCase {

    fun buyChunk(player: Player, buyLandType: LandType) {

        if (!isValidationCheck(player, buyLandType)) {
            return
        }

        player.takeMoney(getLandBuyPrice(buyLandType))
        Land(
            player.chunk.chunkKey,
            LandType.PRIVATE,
            player.uniqueId.toString(),
            mutableListOf(),
            EnvironmentPermission.values().associate { it to it.defaultValue } as MutableMap<EnvironmentPermission, Boolean>
        ).also { LandRepository.create(it.chunkKey.toString(), it) }
        player.sendInfoMessage("토지를 구매하였습니다.")

    }

    private fun getLandBuyPrice(buyLandType: LandType): Long {
        return when (buyLandType) {
            LandType.PRIVATE -> 1792800
            LandType.VILLAGE -> 2988000
            LandType.NATION -> 5976000
            else -> return 0
        }
    }

    private fun isValidationCheck(player: Player, buyLandType: LandType): Boolean {

        if (LandRepository.get(player.chunk.chunkKey.toString()) != null) {
            player.sendErrorMessage("이미 누가 소유하고 있는 토지입니다.")
            return false
        }

        if (isPlayerHasVillageOrNation(player, buyLandType).not()) {
            player.sendErrorMessage("소유하고 있는 마을 또는 국가가 존재하지 않습니다.")
            return false
        }

        if (!isHasMoney(player, buyLandType)) {
            player.sendErrorMessage("소지금액이 부족합니다.")
            return false
        }

        if (!isHasMoreLand(player, buyLandType)) {
            player.sendErrorMessage("구매 가능한 땅의 갯수를 초과 하였습니다.")
            return false
        }

        return true
    }

    private fun isPlayerHasVillageOrNation(player: Player, buyLandType: LandType): Boolean {
        return when (buyLandType) {
            LandType.VILLAGE -> {
                VillageRepository.getList().find { it.owner == player.uniqueId.toString() } == null
            }
            LandType.NATION -> {
                NationRepository.getList().find { it.owner == player.uniqueId.toString() } == null
            }
            else -> true
        }
    }

    private fun isHasMoreLand(player: Player, buyLandType: LandType): Boolean {
        val maxLandCount = when (buyLandType) {
            LandType.PRIVATE -> 9
            LandType.VILLAGE -> VillageRepository.getList()
                .first { it.owner == player.uniqueId.toString() }.member.size * 5
            LandType.NATION -> NationRepository.getList()
                .first { it.owner == player.uniqueId.toString() }.villages.size * 10
            else -> false
        }

        return LandRepository.getList()
            .filter { it.owner == player.uniqueId.toString() }
            .filter { it.type == buyLandType }
            .count() != maxLandCount
    }

    private fun isHasMoney(player: Player, buyLandType: LandType): Boolean {

        if (player.hasMoney() < getLandBuyPrice(buyLandType)) {
            return false
        }

        return true
    }

}