package com.github.donghune.land.model.permission

import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class EnvironmentPermission(
    val korName: String,
    val description: String,
    val defaultValue: Boolean,
) {
    ENDERMAN_GRIEF(
        "엔더맨이 블록을 옮기는 행위",
        "기본 권한으로 설정됨.",
        false
    ),
    SNOW_MELT(
        "눈 녹음",
        "기본 권한으로 설정됨.",
        true
    ),
    ICE_FORM(
        "물이 얼음",
        "기본 권한으로 설정됨.",
        true
    ),
    ICE_MELT(
        "얼음이 녹음",
        "기능이 비활성화 됨.",
        true
    ),
    LEAF_DECAY(
        "나뭇잎이 사라짐",
        "기능이 비활성화 됨.",
        true
    ),
    GRASS_GROWTH(
        "잔디가 퍼짐",
        "기능이 활성화 됨.",
        true
    ),
    FIRE_SPREAD(
        "불이 번짐",
        "기능이 비활성화 됨.",
        true
    ),
    LAVA_FLOW(
        "용암이 흐름",
        "기본 권한으로 설정됨.",
        true
    );

    fun toItemStack(value: Boolean): ItemStack {
        return ItemStackFactory()
            .setType(if (value) Material.GREEN_WOOL else Material.RED_WOOL)
            .setDisplayName("&f$korName".replaceChatColorCode())
            .setLore(listOf("&f$description".replaceChatColorCode()))
            .build()
    }
}