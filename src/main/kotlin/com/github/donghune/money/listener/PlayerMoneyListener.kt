package com.github.donghune.money.listener

import com.github.donghune.hmm.wallet
import com.github.donghune.money.model.entity.CheckPaper
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.nms.getNBTTagCompound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerMoneyListener : Listener {
    @EventHandler
    fun onPlayerInteractMoney(event: PlayerInteractEvent) {
        val player = event.player
        val itemStack = event.item ?: return

        val checkPaper = itemStack.getNBTTagCompound(CheckPaper::class.java) ?: return

        player.wallet.giveMoney(checkPaper.price)
        itemStack.amount -= 1
        player.sendInfoMessage("수표를 사용하여 돈을 획득 하였습니다.")
    }
}
