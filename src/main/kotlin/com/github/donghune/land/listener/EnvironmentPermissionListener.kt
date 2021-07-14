package com.github.donghune.land.listener

import com.github.donghune.land.extension.getLand
import com.github.donghune.land.extension.isStorage
import com.github.donghune.land.model.entity.LandOption
import org.bukkit.Material
import org.bukkit.entity.Enderman
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent


class EnvironmentPermissionListener : Listener {

    @EventHandler
    fun onEntityChangeBlockEvent(event: EntityChangeBlockEvent) {
        val entity = event.entity
        val chunk = event.block.chunk
        val land = chunk.getLand() ?: return

        if (entity !is Enderman) {
            return
        }

        if (land.landOption[LandOption.ENDERMAN_DRIEF] == true) {
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
                if (land.landOption[LandOption.ICE_FORM] == true) {
                    return
                }

                event.isCancelled = true
            }
            Material.ICE -> {
                if (land.landOption[LandOption.ICE_MELT] == true) {
                    return
                }

                event.isCancelled = true
            }
            Material.SNOW_BLOCK -> {
                if (land.landOption[LandOption.SNOW_MELT] == true) {
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
                if (land.landOption[LandOption.LAVA_FLOW] == true) {
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

        if (!(event.cause == IgniteCause.SPREAD || event.cause == IgniteCause.LAVA || event.cause == IgniteCause.LIGHTNING || event.cause == IgniteCause.FIREBALL)) {
            return
        }

        if (land.landOption[LandOption.FIRE_SPREAD] == true) {
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
                if (land.landOption[LandOption.GRASS_GROWTH] == true) {
                    return
                }

                event.isCancelled = true
            }
            else -> return
        }
    }

    @EventHandler
    fun onBlockPlaceEvent(event: BlockPlaceEvent) {
        val block = event.block
        val chunk = block.chunk
        val land = chunk.getLand() ?: return

        if (land.landOption[LandOption.BUILD] == true) {
            return
        }

        if (land.owner == event.player.uniqueId.toString()) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onBlockBreakEvent(event: BlockBreakEvent) {
        val block = event.block
        val chunk = block.chunk
        val land = chunk.getLand() ?: return

        if (land.landOption[LandOption.BUILD] == true) {
            return
        }

        if (land.owner == event.player.uniqueId.toString()) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val chunk = block.chunk
        val land = chunk.getLand() ?: return

        if (block.isStorage() && land.landOption[LandOption.CHEST_ACCESS] == true) {
            return
        }

        if (land.landOption[LandOption.USE] == true) {
            return
        }

        if (land.owner == event.player.uniqueId.toString()) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerMoveEvent(event: PlayerMoveEvent) {
        val player = event.player
        val land = player.chunk.getLand() ?: return

        if (land.landOption[LandOption.ENTRY] == true) {
            return
        }

        if (land.owner == player.uniqueId.toString()) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        val player = event.damager as Player
        val playerLand = player.chunk.getLand()
        val entityLand = entity.chunk.getLand()

        if (entity is ItemFrame) {

            if (entityLand == null) {
                event.isCancelled = true
                return
            }

            if (entityLand.landOption[LandOption.ENTITY_ITEM_FRAME_DESTORY] == true) {
                return
            }

            if (entityLand.owner == player.uniqueId.toString()) {
                return
            }

            event.isCancelled = true

        }

        if (playerLand?.landOption?.get(LandOption.PVP) == true &&
            entityLand?.landOption?.get(LandOption.PVP) == true
        ) {
            return
        }

        event.isCancelled = true
    }

}