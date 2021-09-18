package com.github.donghune.shop.api

import com.github.donghune.shop.model.entity.Stuff
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ShopItemSellEvent(
    val player: Player,
    val stuff: Stuff,
    val amount: Int,
) : Event() {

    val totalMoney: Long = stuff.sellPrice.toLong() * amount

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlerList
        }
    }
}
