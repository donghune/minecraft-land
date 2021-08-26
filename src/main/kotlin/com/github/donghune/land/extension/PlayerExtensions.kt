package com.github.donghune.land.extension

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.sk89q.worldedit.WorldEdit
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
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

fun Player.getPersonalLandList(): List<Land> {
    return LandRepository.getList()
        .filter { it.owner == uniqueId.toString() }
        .filter { it.type == LandType.PERSONAL }
}

fun Village.getVillageLandList(): List<Land> {
    return LandRepository.getList()
        .filter { it.owner == uuid }
        .filter { it.type == LandType.VILLAGE }
}

fun Nation.getNationLandList(): List<Land> {
    return LandRepository.getList()
        .filter { it.owner == uuid }
        .filter { it.type == LandType.NATION }
}

fun Player.isHasMoreLand(buyLandType: LandType): Boolean {
    val maxLandCount = when (buyLandType) {
        LandType.PERSONAL -> 9
        LandType.VILLAGE -> VillageRepository.getList()
            .first { it.owner == uniqueId.toString() }.member.size * 5
        LandType.NATION -> NationRepository.getList()
            .first { it.owner == uniqueId.toString() }.member.size * 10
        else -> false
    }

    return LandRepository.getList()
        .filter { it.owner == uniqueId.toString() }
        .filter { it.type == buyLandType }
        .count() != maxLandCount
}

fun Player.getLandList(): List<Land> = LandRepository.getList().filter { it.owner == uniqueId.toString() }

fun Nation.getMemberList(): List<String> {
    return member.map { nationVillage ->
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

fun OfflinePlayer.toHeadItemStack(): ItemStack {
    return ItemStackFactory()
        .setType(Material.PLAYER_HEAD)
        .SkullMeta { owningPlayer = this@toHeadItemStack }
        .setDisplayName("&f${this.name}".replaceChatColorCode())
        .build()
}

fun UUID.toPlayer(): Player? {
    return Bukkit.getPlayer(this)
}

fun UUID.toOfflinePlayer(): OfflinePlayer {
    return Bukkit.getOfflinePlayer(this)
}

fun OfflinePlayer.sendColorCodeMessage(message: String) {
    if (isOnline) {
        player?.sendMessage(message.replaceChatColorCode())
    }
}

fun OfflinePlayer.sendErrorMessage(message: Any) {
    if (isOnline) {
        player?.sendColorCodeMessage("&c[ERROR] &f$message")
    }
}

fun OfflinePlayer.sendDebugMessage(message: Any) {
    if (isOnline) {
        player?.sendColorCodeMessage("&e[Debug] &f$message")
    }
}

fun OfflinePlayer.sendInfoMessage(message: Any) {
    if (isOnline) {
        player?.sendColorCodeMessage("&9[INFO] &f$message")
    }
}