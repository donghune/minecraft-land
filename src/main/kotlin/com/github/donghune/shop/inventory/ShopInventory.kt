package com.github.donghune.shop.inventory

import com.github.donghune.land.inventory.InvIcon
import com.github.donghune.money.util.edit
import com.github.donghune.money.util.playerMoney
import com.github.donghune.namulibrary.extension.*
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.struct.PagingList
import com.github.donghune.plugin
import com.github.donghune.shop.api.ShopItemSellEvent
import com.github.donghune.shop.model.ShopMessage
import com.github.donghune.shop.model.entity.Shop
import com.github.donghune.util.ItemStackFactory
import com.github.donghune.util.isSimilarTo
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.max
import kotlin.math.min

class ShopInventory(
    private val shop: Shop,
) : GUI(plugin, 54, shop.name) {

    companion object {
        const val TITLE = "상점"

        val ICON_PLAYER_MONEY: (Long) -> ItemStack = { money ->
            ItemStackFactory()
                .setType(Material.GOLD_INGOT)
                .setDisplayName("&6보유금액")
                .addLore("&f${money.toMoneyFormat()}")
                .build()
        }
    }

    private var page: Int = 0
    private val stuffList = PagingList(36, shop.stuffList.toList())

    override suspend fun setContent() {
        setItem(48, InvIcon.ICON_PREVIOUS()) {
            it.isCancelled = true
            page = max(page - 1, 0)
            refreshContent()
        }
        setItem(50, InvIcon.ICON_NEXT()) {
            it.isCancelled = true
            page = min(page + 1, stuffList.lastPageIndex)
            refreshContent()
        }
        stuffList.getPage(page).forEachIndexed { index, stuff ->
            setItem(index, stuff.toItemStack().clone()) {
                val player = it.whoClicked as Player

                it.isCancelled = true

                if (stuff.buyPrice < 0) {
                    return@setItem player.sendErrorMessage("구매 불가능한 아이템 입니다.")
                }

                val amount = when (it.click) {
                    ClickType.LEFT -> 1
                    ClickType.SHIFT_LEFT -> 64
                    else -> return@setItem
                }

                val totalPrice = stuff.buyPrice * amount

                if (player.playerMoney.money <= totalPrice) {
                    return@setItem player.sendErrorMessage("소지금이 부족합니다.")
                }

                if (player.inventory.contents.filterNotNull().any { item -> item.type == Material.AIR }) {
                    return@setItem player.sendErrorMessage("인벤토리에 공간이 없습니다 ")
                }

                player.playerMoney.takeMoney(stuff.buyPrice.toLong())
                player.inventory.addItem(stuff.itemStack)
                player.sendInfoMessage(ShopMessage.BUY_ITEM)
                inventory.setItem(53, ICON_PLAYER_MONEY(player.playerMoney.money))
            }

            inventory.setItem(53, ICON_PLAYER_MONEY(player.playerMoney.money))
        }
    }

    lateinit var player: Player

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
        player = event.player as Player
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
        if (inventory != event.inventory) {
            return
        }

        val inventory = event.view
        val slot = event.rawSlot
        val player = event.whoClicked as Player
        val playerMoney = player.playerMoney

        event.isCancelled = true

        val currentItem = event.currentItem ?: return

        if (currentItem.type == Material.AIR) {
            return
        }

        when {
            // Player Inventory Area
            (54..89).contains(slot) -> {

                val stuff = shop.stuffList.find {
                    it.itemStack.isSimilarTo(currentItem)
                }

                // 해당 아이템을 판매 할 수 없는 경우
                if (stuff == null) {
                    player.sendErrorMessage(ShopMessage.CAN_NOT_SELL_ITEM)
                    return
                }

                if (stuff.sellPrice < 0) {
                    player.sendErrorMessage(ShopMessage.CAN_NOT_SELL_ITEM)
                    return
                }

                // 클릭 타입에 따른 아이템 갯수 설정
                val amount = when (event.click) {
                    ClickType.LEFT -> 1
                    ClickType.SHIFT_LEFT -> currentItem.amount
                    else -> return
                }

                val shopItemSellEvent = ShopItemSellEvent(
                    player,
                    stuff,
                    amount
                )

                // 판매처리
                playerMoney.apply { giveMoney(stuff.sellPrice * amount.toLong()) }.edit()
                currentItem.amount -= amount
                player.sendInfoMessage(
                    ShopMessage.SELL_ITEM.format(
                        stuff.sellPrice.toMoneyFormat(),
                        amount,
                        (stuff.sellPrice * amount).toMoneyFormat()
                    )
                )
                Bukkit.getPluginManager().callEvent(shopItemSellEvent)
            }
            // Out Inventory
            else -> return
        }

        inventory.setItem(53, ICON_PLAYER_MONEY(player.playerMoney.money))
    }
}
