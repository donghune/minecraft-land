package com.github.donghune.land.model.repository

import org.bukkit.entity.Player
import java.util.*

object RegisterRepository {

    private val villageApplicationMap = mutableMapOf<UUID, UUID>()
    private val nationApplicationMap = mutableMapOf<UUID, UUID>()

    fun register(player: Player, target: Player, registerType: RegisterType) {

    }

    fun accept(player: Player) {

    }

    fun decline(player: Player) {

    }

}

enum class RegisterType {
    NATION, VILLAGE
}