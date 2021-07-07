package com.github.donghune.land.listener

import com.github.donghune.land.model.credit_rating.extension.getCreditRating
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class CreditRatingListener : Listener {

    @EventHandler
    fun onAsyncPlayerChatEvent(event: AsyncPlayerChatEvent) {
        val player = event.player
        val tag = player.getCreditRating().getCreditRating().tag
        event.format = "&f[${tag}&f] ".replaceChatColorCode() + "<%1\$s> %2\$s"
    }

}