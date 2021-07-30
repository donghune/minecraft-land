package com.github.donghune.land.inventory.group

import com.github.donghune.land.model.entity.LandType
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandGroupBuildJoinInventory(
    private val landType: LandType
) : GUI(plugin, 27, "${landType.korName} 가입 또는 구축") {

    companion object {
        private val ICON_JOIN: (LandType) -> ItemStack = {
            ItemStackFactory()
                .setType(Material.OAK_SIGN)
                .setDisplayName("&f${it.korName} 가입".replaceChatColorCode())
                .build()
        }
        private val ICON_BUILD: (LandType) -> ItemStack = {
            ItemStackFactory()
                .setType(Material.WRITABLE_BOOK)
                .setDisplayName("&f${it.korName} 구축".replaceChatColorCode())
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
        setItem(11, ICON_JOIN(landType)) {
            it.isCancelled = true
            LandGroupJoinInventory().open(it.whoClicked as Player)
        }
        setItem(15, ICON_BUILD(landType)) {
            it.isCancelled = true
            LandGroupBuildInventory(landType).open(it.whoClicked as Player)
        }
    }
}