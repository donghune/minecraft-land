package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.Land
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class LandTransformPlayerSelectInventory(
    private val land: Land,
) : GUI(plugin, 27, "소유권을 양도할 플레이어를 선택해주세요") {

    lateinit var player: Player

    companion object {
        private val ICON_HEAD: (Player) -> ItemStack = { player ->
            ItemStackFactory()
                .setType(Material.PLAYER_HEAD)
                .setDisplayName(player.name)
                .SkullMeta { owningPlayer = player }
                .build()
        }
    }

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
        player = event.player as Player
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        Bukkit.getOnlinePlayers()
            .filter { it.uniqueId != player.uniqueId }
            .forEachIndexed { index, player ->
                setItem(index, ICON_HEAD(player)) {
                    LandTransformConfirmInventory(land, player).open(it.whoClicked as Player)
                }
            }
    }
}