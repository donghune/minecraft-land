package com.github.donghune.land.inventory

import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object InvIcon {

    val ICON_OK: () -> ItemStack = {
        ItemStackFactory()
            .setType(Material.GREEN_WOOL)
            .setDisplayName("&aOK")
            .build()
    }

    val ICON_CANCEL: () -> ItemStack = {
        ItemStackFactory()
            .setType(Material.GREEN_WOOL)
            .setDisplayName("&aOK")
            .build()
    }

    val ICON_LAND_PERSONAL: () -> ItemStack = {
        ItemStackFactory()
            .setType(Material.GRASS_BLOCK)
            .setDisplayName("&f개인")
            .build()
    }

    val ICON_LAND_VILLAGE: () -> ItemStack = {
        ItemStackFactory()
            .setType(Material.OAK_WOOD)
            .setDisplayName("&f마을")
            .build()
    }

    val ICON_LAND_NATION: () -> ItemStack = {
        ItemStackFactory()
            .setType(Material.IRON_BLOCK)
            .setDisplayName("&f국가")
            .build()
    }

}