package com.github.donghune.land.inventory.real

import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.inventory.LandSelectInventory
import com.github.donghune.land.inventory.group.LandGroupChildInventory
import com.github.donghune.land.inventory.group.LandGroupDeleteConfirmInventory
import com.github.donghune.land.inventory.group.LandVaultUpgradeInventory
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.namulibrary.extension.replaceChatColorCode
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
import java.util.*

class GroupSettingInventory(
    val group: Group
) : GUI(plugin, 27, "${group.getType()} 토지 설정") {

    companion object {
        private val ICON_OWNER: (LandType, UUID) -> ItemStack = { landType, uuid ->
            ItemStackFactory()
                .setType(Material.PLAYER_HEAD)
                .SkullMeta { owningPlayer = Bukkit.getOfflinePlayer(uuid) }
                .setDisplayName("&f${landType.korName} 정보".replaceChatColorCode())
                .build()
        }
        private val ICON_LAND: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GRASS)
                .setDisplayName("&f토지 설정".replaceChatColorCode())
                .build()
        }
        private val ICON_GOLD: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GOLD_INGOT)
                .setDisplayName("&f금고 설정".replaceChatColorCode())
                .build()
        }
        private val ICON_MEMBER: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BOOK)
                .setDisplayName("&f구성원 관리".replaceChatColorCode())
                .build()
        }
        private val ICON_DELETE: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BARRIER)
                .setDisplayName("&f삭제하기".replaceChatColorCode())
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
        setItem(10, ICON_OWNER(group.getType(), group.owner.toUUID())) {
            it.isCancelled = true
        }
        setItem(12, ICON_LAND()) {
            it.isCancelled = true
            LandSelectInventory(group.getType()).open(it.whoClicked as Player)
        }
        setItem(13, ICON_GOLD()) {
            it.isCancelled = true
            LandVaultUpgradeInventory(group).open(it.whoClicked as Player)
        }
        setItem(14, ICON_DELETE()) {
            it.isCancelled = true
            LandGroupDeleteConfirmInventory(group).open(it.whoClicked as Player)
        }
        setItem(15, ICON_MEMBER()) {
            it.isCancelled = true
            LandGroupChildInventory(group).open(it.whoClicked as Player)
        }
    }
}
