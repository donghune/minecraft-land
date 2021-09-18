package com.github.donghune.land.inventory.group

import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.inventory.LandPermissionInventory
import com.github.donghune.land.inventory.LandSellConfirmInventory
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Land
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

class LandGroupSettingInventory(
    val group: Group,
    val land: Land
) : GUI(plugin, 27, "${group.getType().korName} 토지 설정") {

    companion object {
        private val ICON_PERMISSION: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.WRITABLE_BOOK)
                .setDisplayName("&f권한 설정".replaceChatColorCode())
                .build()
        }
        private val ICON_GOLD: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GOLD_INGOT)
                .setDisplayName("&f금고 설정".replaceChatColorCode())
                .build()
        }
        private val ICON_OWNER: (LandType, UUID) -> ItemStack = { landType, uuid ->
            ItemStackFactory()
                .setType(Material.PLAYER_HEAD)
                .SkullMeta { owningPlayer = Bukkit.getOfflinePlayer(uuid) }
                .setDisplayName("&f${landType.korName} 정보".replaceChatColorCode())
                .build()
        }
        private val ICON_DELETE: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BARRIER)
                .setDisplayName("&f토지 삭제".replaceChatColorCode())
                .build()
        }
        private val ICON_MEMBER: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BOOK)
                .setDisplayName("&f구성원 관리".replaceChatColorCode())
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
        setItem(10, ICON_OWNER(land.type, land.owner.toUUID())) {
            it.isCancelled = true
        }
        setItem(12, ICON_PERMISSION()) {
            it.isCancelled = true
            LandPermissionInventory(land).open(it.whoClicked as Player)
        }
        setItem(13, ICON_GOLD()) {
            it.isCancelled = true
            LandVaultUpgradeInventory(group).open(it.whoClicked as Player)
        }
        setItem(14, ICON_DELETE()) {
            it.isCancelled = true
            LandSellConfirmInventory(land).open(it.whoClicked as Player)
        }
        setItem(15, ICON_MEMBER()) {
            it.isCancelled = true
            LandGroupChildInventory(group).open(it.whoClicked as Player)
        }
    }
}
