package com.github.donghune.regenblock.listeners

import com.github.donghune.plugin
import com.github.donghune.regenblock.model.RegenRegionRepository
import com.github.donghune.util.contains2
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class RegenBlockListener : Listener {
    @EventHandler
    fun onBlockBreakEvent(event: BlockBreakEvent) {
        val location = event.block.location
        val regenRegion = RegenRegionRepository.getList()
            .find {
                it.area.contains2(location.toVector()) && it.world.name == location.world.name
            } ?: return

        Bukkit.getScheduler().runTaskLater(
            plugin,
            Runnable { event.block.type = regenRegion.regenRegionInfo.getRandomBlock() },
            regenRegion.regenRegionInfo.regenDelay * 20L
        )
    }
}
