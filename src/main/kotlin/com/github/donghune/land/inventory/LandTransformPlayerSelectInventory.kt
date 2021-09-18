package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.Land
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

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

    // TODO: 2021/08/27 양도는 uuid 기준으로 되어야 함. 그래서 개인 별 그룹 별로 나눠야 할 것 같음.
    override suspend fun setContent() {
        Bukkit.getOnlinePlayers()
            .filter { it.uniqueId != player.uniqueId }
            .forEachIndexed { index, target ->
                setItem(index, ICON_HEAD(target)) {
                    LandTransformConfirmInventory(land, it.whoClicked as Player, target).open(it.whoClicked as Player)
                }
            }
    }
}
