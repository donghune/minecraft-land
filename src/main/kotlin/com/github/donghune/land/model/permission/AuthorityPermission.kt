package com.github.donghune.land.model.permission

import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class AuthorityPermission(
    val korName: String,
    val description: String,
    val defaultValue: Boolean,
) {
    BUILD(
        "건축 권한",
        "기본 권한으로 설정됨.(기본 권한 : 소유자만 가능)",
        false
    ),
    PVP(
        "PVP 권한",
        "기본 권한으로 설정됨.(기본 권한 : 소유자만 가능)",
        false
    ),
    CHEST_ACCESS(
        "보관 기능 블록 사용 권한",
        "소유자만 가능으로 설정됨.",
        false
    ),
    USE(
        "기능 블록 사용 권한",
        "기본 권한으로 설정됨.(기본 권한 : 소유자만 가능)",
        false
    ),
    ENTRY(
        "토지 입장 가능 권한",
        "친구 접근 가능으로 설정됨.",
        false
    ),
    ENTITY_ITEM_FRAME_DESTROY(
        "아이템 프레임 파괴 권한",
        "기본 권한으로 설정됨.(기본 권한 : 소유자만 가능)",
        false
    );

    fun toItemStack(value: Boolean): ItemStack {
        return ItemStackFactory()
            .setType(if (value) Material.GREEN_WOOL else Material.RED_WOOL)
            .setDisplayName("&f$korName".replaceChatColorCode())
            .setLore(listOf("&f$description".replaceChatColorCode()))
            .build()
    }
}