package com.github.donghune.money.util

import com.github.donghune.money.model.PlayerMoneyRepository
import com.github.donghune.money.model.entity.PlayerMoney
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

val Player.playerMoney: PlayerMoney
    get() = PlayerMoneyRepository.getSafety(uniqueId.toString())

val OfflinePlayer.playerMoney: PlayerMoney?
    get() = PlayerMoneyRepository.get(uniqueId.toString())

fun PlayerMoney.edit() {
    PlayerMoneyRepository.update(this.uuid, this)
}
