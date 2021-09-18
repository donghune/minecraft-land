package com.github.donghune.land.extension

import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.TaxConfigRepository
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.rating.model.extension.getCreditRating
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * 개인의 세금 처리
 */
fun Player.paymentTax() {
    val taxPrice = getTaxPrice()

    if (hasMoney(taxPrice).not()) {
        return
    }

    takeMoney(taxPrice.toLong())
    sendErrorMessage("보유금액에서 ${taxPrice.toMoneyFormat()} 만큼 세금이 지불되었습니다.")
}

/**
 * 그룹의 세금 처리
 */
fun Group.paymentTax() {
    if (isTaxFree()) {
        return
    }

    val taxPrice = getTaxPrice()

    if (!hasMoney(taxPrice)) {
        return
    }

    withdrawal(taxPrice)
    getOwnerPlayer().sendInfoMessage("${getType().korName} 세금이 납부되었습니다.")
}

/**
 * 개인의 세금 가격을 책정 합니다.
 */
fun Player.getTaxPrice(): Int {
    val taxPrice = TaxConfigRepository.get().personalTaxTable[getCreditRating().getCreditRating().rating] ?: 0
    val landCount = getLandList().size
    return taxPrice * landCount
}

/**
 * 그룹의 세금 가격을 책정 합니다.
 */
fun Group.getTaxPrice(): Int {
    val taxPrice = TaxConfigRepository.get().groupTaxTable[getRating()] ?: 0
    val landCount = getLandList().size
    return taxPrice * landCount
}

// 마을이 개인에게 세금 부과: (마을 토지 세금 1/n + 국가 토지 세금 1/n)*1.5
fun Player.paymentToVillage() {
    val village = getBelongingVillage() ?: return
    val n = village.getMembers().size
    val villageLandTax = getTaxPrice()
    val nationLandTax = (getBelongingNation() ?: return).getTaxPrice()
    val taxPrice = ((villageLandTax / n + nationLandTax / n) * TaxConfigRepository.get().personalToVillageTax).toLong()
    takeMoney(taxPrice)
    village.vaultGold += taxPrice
    sendInfoMessage("마을에 세금을 납부하였습니다.")
}

// 국가가 마을에게 세금 부과: (국가 토지 세금 1/n)*1.5
fun Village.paymentToNation() {
    val nation = getBelongingNation() ?: return
    val n = nation.getMembers().size
    val nationLandTax = nation.getTaxPrice() / n
    val taxPrice = ((nationLandTax / n) * TaxConfigRepository.get().villageToNationTax).toInt()

    vaultGold -= taxPrice
    nation.vaultGold += taxPrice
    Bukkit.getPlayer(owner)?.sendInfoMessage("국가 세금을 납부하였습니다.")
}

fun Village.getBelongingNation(): Nation? {
    return NationRepository.getList()
        .find { it.getMembers().contains(it.uuid) }
}
