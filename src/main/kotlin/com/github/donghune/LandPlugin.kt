package com.github.donghune

import com.github.donghune.deprecated.NBoxRepository
import com.github.donghune.land.command.LandCommand
import com.github.donghune.land.listener.EventListener
import com.github.donghune.land.model.entity.*
import com.github.donghune.land.model.repository.LandConfigRepository
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.TaxConfigRepository
import com.github.donghune.land.schduler.PersonalTaxScheduler
import com.github.donghune.land.schduler.PlayerLocationScheduler
import com.github.donghune.money.MoneyPlugin
import com.github.donghune.namulibrary.schedular.SchedulerManager
import com.github.donghune.rating.command.CreditRatingCommand
import com.github.donghune.rating.listener.CreditRatingListener
import com.github.donghune.rating.model.entity.CreditRating
import com.github.donghune.rating.model.entity.CreditRatingConfig
import com.github.donghune.rating.model.entity.PlayerCreditRating
import com.github.donghune.regenblock.RegenBlockPlugin
import com.github.donghune.regenblock.model.RegenRegionInfoRepository
import com.github.donghune.regenblock.model.RegenRegionRepository
import com.github.donghune.shop.ShopPlugin
import com.github.donghune.shop.model.repo.ShopRepository
import com.github.donghune.warp.WarpPlugin
import com.github.donghune.warp.model.WarpConfigRepository
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class LandPlugin : JavaPlugin() {

    companion object {
        lateinit var moneyPlugin: MoneyPlugin
        lateinit var shopPlugin: ShopPlugin
        lateinit var regenBlockPlugin: RegenBlockPlugin
        lateinit var warpPlugin: WarpPlugin
    }

    init {
        SchedulerManager.initializeSchedulerManager(this)
    }

    override fun onEnable() {
        plugin = this

        /**
        o 플레이어의 보유액 정보가 변경되는 즉시 파일의 정보를 수정
        o 신용 등급을 올려도 결제되지 않음(신용등급 자체는 올라감)
        o 플레이어가 접속중이지 않을때 관리자 명령어로 신용등급 조정이 안됨
        ? 토지를 사면 땅으로 빨려들어가는 것을 수정할 필요가 있음
        o 개인 토지 세금을 플레이어 플레이 타임 기준으로 변경
        x 토지 경계 벽에비비면 승천함
         */

        ConfigurationSerialization.registerClass(CreditRating::class.java)
        ConfigurationSerialization.registerClass(CreditRatingConfig::class.java)
        ConfigurationSerialization.registerClass(PlayerCreditRating::class.java)
        ConfigurationSerialization.registerClass(Land::class.java)
        ConfigurationSerialization.registerClass(Village::class.java)
        ConfigurationSerialization.registerClass(Nation::class.java)
        ConfigurationSerialization.registerClass(RegisterRequest::class.java)

        ConfigurationSerialization.registerClass(TaxConfig::class.java)
        ConfigurationSerialization.registerClass(VaultConfig::class.java)
        ConfigurationSerialization.registerClass(PlayTimeConfig::class.java)

        LandCommand.initialize()
        CreditRatingCommand.initialize()

        Bukkit.getPluginManager().registerEvents(CreditRatingListener(), this)
        Bukkit.getPluginManager().registerEvents(EventListener(), this)

        SchedulerManager.initializeSchedulerManager(this)
        Bukkit.getScheduler().runTaskTimer(this, PlayerLocationScheduler, 0L, 1L)
        PersonalTaxScheduler.runSecond(1L, Int.MAX_VALUE)

        // priority 1
        if (NBoxRepository.getList().isEmpty()) {
            NBoxRepository.createDefaultData("example")
        }
        logger.info("Load complete nBox Plugin...!")

        moneyPlugin = MoneyPlugin(File(dataFolder.path + "/money"), this).apply { onEnable() }
        logger.info("Load complete money plugin...!")

        shopPlugin = ShopPlugin(File(dataFolder.path + "/shop"), this).apply { onEnable() }
        logger.info("Load complete shop plugin...!")

        regenBlockPlugin = RegenBlockPlugin(File(dataFolder.path + "/regenblock"), this).apply { onEnable() }
        logger.info("Load complete regenblock plugin...!")

        warpPlugin = WarpPlugin(File(dataFolder.path + "/warp"), this).apply { onEnable() }
        logger.info("Load complete warp plugin...!")

        if (ShopRepository.getList().isEmpty()) {
            ShopRepository.createDefaultData("example")
        }
        if (RegenRegionInfoRepository.getList().isEmpty()) {
            RegenRegionInfoRepository.createDefaultData("example")
        }
        if (RegenRegionRepository.getList().isEmpty()) {
            RegenRegionRepository.createDefaultData("example")
        }
        WarpConfigRepository.get()
    }

    override fun onDisable() {
        moneyPlugin.onDisable()
        shopPlugin.onDisable()
        regenBlockPlugin.onDisable()

        LandRepository.getList().forEach { it.save() }
    }

    private fun Land.save() {
        LandRepository.save(chunkKey.toString())
    }
}
