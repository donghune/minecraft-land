package com.github.donghune.money

import com.github.donghune.money.command.MoneyCommand
import com.github.donghune.money.listener.PlayerMoneyListener
import com.github.donghune.money.model.entity.PlayerMoney
import com.github.donghune.namulibrary.struct.SubMainManager
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class MoneyPlugin(dataFolder: File, val plugin: JavaPlugin) : SubMainManager(dataFolder, plugin) {

    override fun onDisable() {
    }

    override fun setupCommands() {
        MoneyCommand.initialize()
    }

    override fun setupListeners() {
        Bukkit.getPluginManager().apply {
            registerEvents(PlayerMoneyListener(), plugin)
        }
    }

    override fun setupRegisterClass() {
        ConfigurationSerialization.registerClass(PlayerMoney::class.java, "PlayerMoney")
    }

    override fun setupSchedulers() {
    }
}
