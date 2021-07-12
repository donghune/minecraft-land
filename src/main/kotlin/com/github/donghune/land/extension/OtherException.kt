package com.github.donghune.land.extension

import org.bukkit.Material
import org.bukkit.block.Block

fun Block.isStorage() : Boolean {
    return type == Material.CHEST ||
            type == Material.CHEST_MINECART ||
            type == Material.ENDER_CHEST ||
            type == Material.TRAPPED_CHEST ||
            type == Material.BLACK_SHULKER_BOX ||
            type == Material.BLUE_SHULKER_BOX ||
            type == Material.BROWN_SHULKER_BOX ||
            type == Material.CYAN_SHULKER_BOX ||
            type == Material.GRAY_SHULKER_BOX ||
            type == Material.GREEN_SHULKER_BOX ||
            type == Material.LIGHT_BLUE_SHULKER_BOX ||
            type == Material.LIGHT_GRAY_SHULKER_BOX ||
            type == Material.LIME_SHULKER_BOX ||
            type == Material.PINK_SHULKER_BOX ||
            type == Material.MAGENTA_SHULKER_BOX ||
            type == Material.PURPLE_SHULKER_BOX ||
            type == Material.YELLOW_SHULKER_BOX ||
            type == Material.WHITE_SHULKER_BOX ||
            type == Material.SHULKER_BOX ||
            type == Material.ORANGE_SHULKER_BOX ||
            type == Material.RED_SHULKER_BOX ||
            type == Material.BARREL ||
            type == Material.HOPPER ||
            type == Material.HOPPER_MINECART
}