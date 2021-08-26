package com.github.donghune.land.inventory.group

import com.github.donghune.land.extension.getNation
import com.github.donghune.land.extension.getVillage
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandVaultUpgradeInventory(
    val group: Group,
) : GUI(plugin, 27, "") {

    companion object {
        private val ICON_GOLD: (Int, Int) -> ItemStack = { level, gold ->
            ItemStackFactory()
                .setType(Material.GOLD_INGOT)
                .setDisplayName("&f현재 금고 정보".replaceChatColorCode())
                .addLore("")
                .addLore("&f현재 금고 레벨 : $level")
                .addLore("&f금고 잔여 금액 : ${gold.toMoneyFormat()}")
                .build()
        }

        private val ICON_UPGRADE: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GOLDEN_PICKAXE)
                .setDisplayName("&f금고 증축".replaceChatColorCode())
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
        val (level, gold) = group.vaultLevel to group.vaultGold

        setItem(11, ICON_GOLD(level, gold)) {
            it.isCancelled = true
        }

        setItem(15, ICON_UPGRADE()) {
            it.isCancelled = true
            LandVaultUpgradeConfirmInventory().open(it.whoClicked as Player)
        }
    }
}