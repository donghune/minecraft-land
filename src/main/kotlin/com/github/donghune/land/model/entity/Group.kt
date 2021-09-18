package com.github.donghune.land.model.entity

import com.github.donghune.land.extension.toUUID
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

abstract class Group(
    val uuid: String,
    val name: String,
    val owner: String,
    val child: MutableList<String>,
    val vaultLevel: Int,
    var vaultGold: Long,
    val createdAt: Date,
) {

    abstract fun getRating(): Int
    abstract fun getType(): LandType
    abstract fun getMembers(): List<String>
    abstract fun getLandList(): List<Land>

    fun getOwnerPlayer(): OfflinePlayer {
        return Bukkit.getOfflinePlayer(owner.toUUID())
    }

    fun isTaxFree(): Boolean {
        return ((createdAt.time / 1000) - Date().time) <= 3600
    }

    fun deposit(amount: Int) {
        vaultGold += amount
    }

    fun withdrawal(amount: Int) {
        vaultGold -= amount
    }

    fun hasMoney(amount: Int): Boolean {
        return amount <= vaultGold
    }
}
