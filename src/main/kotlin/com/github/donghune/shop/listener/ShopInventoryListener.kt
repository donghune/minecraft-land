package com.github.donghune.shop.listener

import com.github.donghune.namulibrary.extension.*
import com.github.donghune.shop.inventory.ShopInventory
import com.github.donghune.shop.model.repo.ShopRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

class ShopInventoryListener : Listener {
    @EventHandler
    fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {
        val npcName = event.rightClicked.name ?: return
        val shop = ShopRepository.getList().find { it.npcName == npcName } ?: return
        ShopInventory(shop).open(event.player)
    }
}
