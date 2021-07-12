package com.github.donghune.land.extension

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import com.sk89q.worldedit.session.SessionManager
import com.sk89q.worldedit.session.SessionOwner
import org.bukkit.entity.Player
import org.bukkit.util.BoundingBox
import java.util.*

fun Player.hasMoney(): Long {
    return Long.MAX_VALUE
}

fun Player.giveMoney(money: Long) {

}

fun Player.takeMoney(money: Long) {

}

fun Player.getSelection(): BoundingBox {
    val session = WorldEdit.getInstance().sessionManager.findByName(name)!!
    return session.getSelection(session.selectionWorld).run {
        BoundingBox(
            minimumPoint.x.toDouble(),
            minimumPoint.y.toDouble(),
            minimumPoint.z.toDouble(),
            maximumPoint.x.toDouble(),
            maximumPoint.y.toDouble(),
            maximumPoint.z.toDouble()
        )
    }
}

fun Player.getLandList(): List<Land> = LandRepository.getList().filter { it.owner == uniqueId.toString() }

fun Nation.getMemberList(): List<String> {
    return villages.map { nationVillage ->
        VillageRepository.getList().filter { it.uuid == nationVillage }
    }.flatten()
        .map { it.member }
        .flatten()
}

fun Player.getBelongingVillage(): Village? {
    return VillageRepository.getList()
        .find { village -> village.owner == uniqueId.toString() || village.member.contains(uniqueId.toString()) }
}

fun Player.getBelongingNation(): Nation? {
    return NationRepository.getList()
        .find { nation -> nation.owner == uniqueId.toString() || nation.getMemberList().contains(uniqueId.toString()) }
}