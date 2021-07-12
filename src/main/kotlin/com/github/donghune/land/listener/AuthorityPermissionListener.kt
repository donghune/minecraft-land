package com.github.donghune.land.listener

import com.github.donghune.land.extension.getLand
import com.github.donghune.land.extension.isStorage
import com.github.donghune.land.model.permission.AuthorityPermission
import com.sk89q.worldedit.event.platform.BlockInteractEvent
import org.bukkit.Bukkit
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent


class AuthorityPermissionListener : Listener {

    @EventHandler
    fun onBlockPlaceEvent(event: BlockPlaceEvent) {
        val block = event.block
        val chunk = block.chunk
        val land = chunk.getLand() ?: return

        if (land.authorityPermission[AuthorityPermission.BUILD] == true) {
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

        if (land.authorityPermission[AuthorityPermission.BUILD] == true) {
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

        if (block.isStorage() && land.authorityPermission[AuthorityPermission.CHEST_ACCESS] == true) {
            return
        }

        if (land.authorityPermission[AuthorityPermission.USE] == true) {
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

        if (land.authorityPermission[AuthorityPermission.ENTRY] == true) {
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

            if (entityLand.authorityPermission[AuthorityPermission.ENTITY_ITEM_FRAME_DESTROY] == true) {
                return
            }

            if (entityLand.owner == player.uniqueId.toString()) {
                return
            }

            event.isCancelled = true

        }

        if (entity is Player) {
            if (playerLand?.authorityPermission?.get(AuthorityPermission.PVP) == true &&
                entityLand?.authorityPermission?.get(AuthorityPermission.PVP) == true
            ) {
                return
            }
        }

        event.isCancelled = true
    }


}