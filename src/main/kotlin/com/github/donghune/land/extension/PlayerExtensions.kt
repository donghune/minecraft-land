package com.github.donghune.land.extension

import com.github.donghune.land.model.land.entity.Nation
import com.github.donghune.land.model.land.entity.Village
import com.github.donghune.land.model.land.repository.NationRepository
import com.github.donghune.land.model.land.repository.VillageRepository
import org.bukkit.entity.Player
import java.util.*

fun Player.hasMoney(): Long {
    return 0
}

fun Player.giveMoney(money: Long) {

}

fun Player.takeMoney(money: Long) {

}

fun Nation.getMemberList(): List<UUID> {
    return villages.map { nationVillage ->
        VillageRepository.getList().filter { it.uuid == nationVillage }
    }.flatten()
        .map { it.member }
        .flatten()
}

fun Player.getBelongingVillage(): Village? {
    return VillageRepository.getList()
        .find { village ->  village.owner == uniqueId || village.member.contains(uniqueId) }
}

fun Player.getBelongingNation(): Nation? {
    return NationRepository.getList()
        .find { nation -> nation.owner == uniqueId || nation.getMemberList().contains(uniqueId) }
}