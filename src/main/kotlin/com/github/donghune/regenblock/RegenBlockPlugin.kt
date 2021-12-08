package com.github.donghune.regenblock

import com.github.donghune.namulibrary.struct.SubMainManager
import com.github.donghune.regenblock.listeners.RegenBlockListener
import com.github.donghune.regenblock.model.RegenRegion
import com.github.donghune.regenblock.model.RegenRegionInfo
import com.github.donghune.regenblock.model.RegenRegionRepository
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class RegenBlockPlugin(
    dataFolder: File,
    val plugin: JavaPlugin,
) : SubMainManager(dataFolder, plugin) {

    override fun onDisable() {
        RegenRegionRepository.getList().forEach { RegenRegionRepository.save(it.name) }
    }

    override fun setupCommands() {
    }

    override fun setupListeners() {
        registerEvent(RegenBlockListener())
    }

    override fun setupRegisterClass() {
        registerClass(RegenRegion::class.java)
        registerClass(RegenRegionInfo::class.java)
    }

    override fun setupSchedulers() {
    }
}
