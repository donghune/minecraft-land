package com.github.donghune.land.inventory.nation

import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.struct.PagingList
import com.github.donghune.plugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class NationJoinInventory : GUI(plugin, 27, "국가 가입") {

    companion object {
        private val ICON_PREVIOUS: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                .setDisplay("&f이전")
                .build()
        }
        private val ICON_NEXT: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                .setDisplay("&f다음")
                .build()
        }
        private val ICON_EMPTY: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GREEN_STAINED_GLASS_PANE)
                .setDisplay("")
                .build()
        }
        private val ICON_HEAD: (Nation) -> ItemStack = {
            ItemStackFactory()
                .setType(Material.SKELETON_SKULL)
                .setDisplayName("&f다음")
                .SkullMeta { owningPlayer = Bukkit.getOfflinePlayer(UUID.fromString(it.owner)) }
                .build()
        }
    }

    private val nationList = PagingList(45, NationRepository.getList())
    private var page = 0

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        (44..53).forEach { index -> setItem(index, ICON_EMPTY()) { it.isCancelled = true } }
        setItem(47, ICON_PREVIOUS()) {

        }
        setItem(51, ICON_NEXT()) {

        }

        (0 until 45).forEach { index -> setItem(index, ItemStack(Material.AIR)) }
        nationList.getPage(page).forEachIndexed { index, nation ->
            setItem(index, ICON_HEAD(nation)) {

            }
        }
    }
}