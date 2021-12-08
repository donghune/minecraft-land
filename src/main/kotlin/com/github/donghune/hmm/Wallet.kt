package com.github.donghune.hmm

import com.github.donghune.money.model.PlayerMoneyRepository
import com.github.donghune.money.model.entity.PlayerMoney
import com.github.donghune.rating.model.entity.CreditRating
import com.github.donghune.rating.model.entity.PlayerCreditRating
import com.github.donghune.rating.model.extension.getCreditRating
import org.bukkit.OfflinePlayer
import org.bukkit.block.data.type.Wall
import org.bukkit.entity.Player

val Player.wallet: Wallet
    get() = Wallet(this)

val OfflinePlayer.wallet: Wallet
    get() = Wallet(this)

class Wallet(
    private val offlinePlayer: OfflinePlayer,
) {
    private val uuid: String
        get() = offlinePlayer.uniqueId.toString()

    private val playerMoney: PlayerMoney
        get() = PlayerMoneyRepository.getSafety(uuid)

    private val playerCreditRating: PlayerCreditRating
        get() = offlinePlayer.getCreditRating()

    fun getMoney(): Long {
        return playerMoney.money
    }

    fun hasMoney(targetAmount: Long): Boolean {
        return playerMoney.money >= targetAmount
    }

    fun giveMoney(targetAmount: Long) {
        playerMoney.money += targetAmount
        PlayerMoneyRepository.save(playerMoney.uuid)
    }

    fun takeMoney(targetAmount: Long) {
        playerMoney.money -= targetAmount
        PlayerMoneyRepository.save(playerMoney.uuid)
    }

    fun setMoney(targetAmount: Long) {
        playerMoney.money = targetAmount
        PlayerMoneyRepository.save(playerMoney.uuid)
    }

    fun hasMoney(targetAmount: Int): Boolean {
        return playerMoney.money >= targetAmount
    }

    fun giveMoney(targetAmount: Int) {
        playerMoney.money += targetAmount
        PlayerMoneyRepository.save(playerMoney.uuid)
    }

    fun takeMoney(targetAmount: Int) {
        playerMoney.money -= targetAmount
        PlayerMoneyRepository.save(playerMoney.uuid)
    }

    fun setMoney(targetAmount: Int) {
        playerMoney.money = targetAmount.toLong()
        PlayerMoneyRepository.save(playerMoney.uuid)
    }

    fun getCreditGrade(): CreditRating {
        return playerCreditRating.getCreditRating()
    }

    fun upCreditRating() {
        playerCreditRating.ratingId++
        PlayerMoneyRepository.save(uuid)
        takeMoney(getCreditGrade().price)
    }

    fun dropCreditRating() {
        playerCreditRating.ratingId--
        PlayerMoneyRepository.save(uuid)
        takeMoney(getCreditGrade().price)
    }

}