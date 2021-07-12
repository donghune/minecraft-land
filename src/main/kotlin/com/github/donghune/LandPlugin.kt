package com.github.donghune

import com.github.donghune.rating.command.CreditRatingCommand
import com.github.donghune.land.command.LandCommand
import com.github.donghune.land.listener.AuthorityPermissionListener
import com.github.donghune.land.listener.EnvironmentPermissionListener
import com.github.donghune.rating.listener.CreditRatingListener
import com.github.donghune.rating.model.entity.CreditRating
import com.github.donghune.rating.model.entity.CreditRatingConfig
import com.github.donghune.rating.model.entity.PlayerCreditRating
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.permission.EnvironmentPermission
import com.github.donghune.land.model.repository.LandRepository
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class LandPlugin : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        LandCommand.initialize()
        CreditRatingCommand.initialize()

        Bukkit.getPluginManager().registerEvents(CreditRatingListener(), this)
        Bukkit.getPluginManager().registerEvents(EnvironmentPermissionListener(), this)
        Bukkit.getPluginManager().registerEvents(AuthorityPermissionListener(), this)

        ConfigurationSerialization.registerClass(CreditRating::class.java)
        ConfigurationSerialization.registerClass(CreditRatingConfig::class.java)
        ConfigurationSerialization.registerClass(PlayerCreditRating::class.java)
        ConfigurationSerialization.registerClass(Land::class.java)
        ConfigurationSerialization.registerClass(Village::class.java)
        ConfigurationSerialization.registerClass(Nation::class.java)
    }

    override fun onDisable() {
        LandRepository.getList().forEach { LandRepository.save(it.chunkKey.toString()) }
    }
}