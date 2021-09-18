package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.usecase.TransferLandUseCase
import com.github.donghune.land.model.usecase.TransferLandUseCaseParam
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandTransformConfirmInventory(
    private val land: Land,
    private val player: Player,
    private val target: Player,
) : GUI(plugin, 27, "소유권을 양도하시겠습니까?") {

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
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, InvIcon.ICON_OK()) {
            it.isCancelled = true
            TransferLandUseCase(TransferLandUseCaseParam(it.whoClicked as Player, land, target))
            (it.whoClicked as Player).closeInventory()
        }
        setItem(13, ICON_HEAD(player)) {
            it.isCancelled = true
        }

        setItem(15, InvIcon.ICON_CANCEL()) {
            it.isCancelled = true
            (it.whoClicked as Player).closeInventory()
        }
    }
}
