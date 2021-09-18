package com.github.donghune.land.model.usecase

import com.github.donghune.land.extension.giveMoney
import com.github.donghune.land.extension.sendInfoMessage
import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.model.config.pref
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.namulibrary.extension.toMoneyFormat
import org.bukkit.Bukkit

data class SellLandUseCaseParam(
    val land: Land,
)

object SellLandUseCase : BaseUseCase<SellLandUseCaseParam, Unit>() {
    override fun validation(param: SellLandUseCaseParam): Boolean {
        return true
    }

    override fun execute(param: SellLandUseCaseParam) {
        val land = param.land

        when (land.type) {
            LandType.NONE -> return
            LandType.PERSONAL -> {
                val offlinePlayer = Bukkit.getOfflinePlayer(land.owner.toUUID())
                offlinePlayer.giveMoney(pref.personalLandSellPrice.toLong())
                offlinePlayer.sendInfoMessage("${pref.personalLandSellPrice.toLong().toMoneyFormat()} 이 지급되었습니다.")
            }
            LandType.VILLAGE -> {
                VillageRepository.get(land.owner)!!.getMembers().also {
                    val count = it.size
                    val sellPrice = pref.villageLandSellPrice / count
                    it.map { uuidStr -> uuidStr.toUUID() }
                        .forEach { uuid ->
                            val offlinePlayer = Bukkit.getOfflinePlayer(uuid)
                            offlinePlayer.giveMoney(sellPrice.toLong())
                            offlinePlayer.sendInfoMessage("${sellPrice.toLong().toMoneyFormat()} 이 지급되었습니다.")
                        }
                }
            }
            LandType.NATION -> {
                NationRepository.get(land.owner)!!.getMembers().also {
                    val count = it.size
                    val sellPrice = pref.nationLandSellPrice / count
                    it.forEach { uuidStr ->
                        VillageRepository.get(uuidStr.toUUID())!!.vaultGold += sellPrice
                    }
                }
            }
        }

        LandRepository.remove(land.chunkKey.toString())
    }
}
