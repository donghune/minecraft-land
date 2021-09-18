package com.github.donghune.money.model.entity

import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.nms.addNBTTagCompound
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class CheckPaper(
    val publisher: String,
    val price: Int,
) {
    fun toItemStack(): ItemStack {
        return ItemStackFactory()
            .setType(Material.PAPER)
            .setDisplayName("수표")
            .setLore(
                listOf(
                    "&f금액 : &6${price.toMoneyFormat()}",
                    "&f출처 : &6$publisher"
                )
            )
            .build()
            .apply { addNBTTagCompound(this@CheckPaper) }
    }
}
