package com.github.donghune.shop

import com.github.donghune.namulibrary.struct.SubMainManager
import com.github.donghune.shop.command.*
import com.github.donghune.shop.listener.ShopInventoryListener
import com.github.donghune.shop.model.entity.Shop
import com.github.donghune.shop.model.entity.Stuff
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ShopPlugin(dataFolder: File, val plugin: JavaPlugin) : SubMainManager(dataFolder, plugin) {

    override fun onDisable() {
    }

    override fun setupCommands() {
    }

    override fun setupListeners() {
        Bukkit.getPluginManager().apply {
            registerEvents(ShopInventoryListener(), plugin)
        }
    }

    override fun setupRegisterClass() {
        ConfigurationSerialization.registerClass(Shop::class.java, "Shop")
        ConfigurationSerialization.registerClass(Stuff::class.java, "Stuff")
    }

    override fun setupSchedulers() {
    }
}
