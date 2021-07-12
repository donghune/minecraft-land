package com.github.donghune.land.listener

import com.github.donghune.land.extension.getLand
import com.github.donghune.land.model.permission.EnvironmentPermission
import org.bukkit.Material
import org.bukkit.entity.Enderman
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause
import org.bukkit.event.entity.EntityChangeBlockEvent


class EnvironmentPermissionListener : Listener {

    @EventHandler
    fun onEntityChangeBlockEvent(event: EntityChangeBlockEvent) {
        val entity = event.entity
        val chunk = event.block.chunk
        val land = chunk.getLand() ?: return

        if (entity !is Enderman) {
            return
        }

        if (land.environmentPermission[EnvironmentPermission.ENDERMAN_GRIEF] == true) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onBlockFadeEvent(event: BlockFadeEvent) {
        val chunk = event.block.chunk
        val land = chunk.getLand() ?: return

        println(event.block.type)
        println(land)

        when (event.block.type) {
            Material.WATER -> {
                if (land.environmentPermission[EnvironmentPermission.ICE_FORM] == true) {
                    return
                }

                event.isCancelled = true
            }
            Material.ICE -> {
                if (land.environmentPermission[EnvironmentPermission.ICE_MELT] == true) {
                    return
                }

                event.isCancelled = true
            }
            Material.SNOW_BLOCK -> {
                if (land.environmentPermission[EnvironmentPermission.SNOW_MELT] == true) {
                    return
                }

                event.isCancelled = true
            }
            else -> return
        }
    }

    @EventHandler
    fun onBlockFromToEvent(event: BlockFromToEvent) { // LAVA_FLOW
        val chunk = event.block.chunk
        val land = chunk.getLand() ?: return

        when (event.block.type) {
            Material.LAVA -> {
                if (land.environmentPermission[EnvironmentPermission.LAVA_FLOW] == true) {
                    return
                }

                event.isCancelled = true
            }
            else -> {
            }
        }
    }

    @EventHandler
    fun onBlockIgniteEvent(event: BlockIgniteEvent) {
        val chunk = event.block.chunk
        val land = chunk.getLand() ?: return

        if (!(event.cause == IgniteCause.SPREAD || event.cause == IgniteCause.LAVA || event.cause == IgniteCause.LIGHTNING || event.cause == IgniteCause.FIREBALL)){
            return
        }

        if (land.environmentPermission[EnvironmentPermission.FIRE_SPREAD] == true) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onBlockSpreadEvent(event: BlockSpreadEvent) {
        val chunk = event.block.chunk
        val land = chunk.getLand() ?: return

        when (event.block.type) {
            Material.DIRT -> {
                if (land.environmentPermission[EnvironmentPermission.GRASS_GROWTH] == true) {
                    return
                }

                event.isCancelled = true
            }
            else -> return
        }
    }

}