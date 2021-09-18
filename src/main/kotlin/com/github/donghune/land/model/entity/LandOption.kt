package com.github.donghune.land.model.entity

import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class LandOption(
    val korName: String,
    val optionType: OptionType,
    val description: String,
    val defaultValue: Boolean,
) {
    ENTRANCE(
        "입장",
        OptionType.PERMISSION,
        "활성화 시 입장이 가능 합니다.\n비 활성화 시 입장이 불가능 합니다.",
        true
    ),
    EXIT(
        "퇴장",
        OptionType.PERMISSION,
        "활성화 시 퇴장이 가능 합니다.\n비 활성화 시 퇴장이 불가능 합니다.",
        true
    ),
    INTERACTION(
        "상호 작용",
        OptionType.PERMISSION,
        "활성화 시 상호 작용이 가능 합니다.\n비 활성화 시 상호 작용이 불가능 합니다.",
        true
    ),
    ATTACK_ENTITY(
        "엔티티 공격",
        OptionType.PERMISSION,
        "활성화 시 엔티티 공격가 가능 합니다.\n비 활성화 시 엔티티 공격가 불가능 합니다.",
        true
    ),
    ATTACK_PLAYER(
        "플레이어 공격",
        OptionType.PERMISSION,
        "활성화 시 플레이어 공격가 가능 합니다.\n비 활성화 시 플레이어 공격가 불가능 합니다.",
        true
    ),
    PROJECTILE_LAUNCH(
        "발사체 발사",
        OptionType.PERMISSION,
        "활성화 시 발사체 발사가 가능 합니다.\n비 활성화 시 발사체 발사가 불가능 합니다.",
        true
    ),
    BLOCK_BREAK(
        "블럭 파괴",
        OptionType.PERMISSION,
        "활성화 시 블럭 파괴가 가능 합니다.\n비 활성화 시 블럭 파괴가 불가능 합니다.",
        true
    ),
    BLOCK_PLACE(
        "블럭 설치",
        OptionType.PERMISSION,
        "활성화 시 블럭 설치가 가능 합니다.\n비 활성화 시 블럭 설치가 불가능 합니다.",
        true
    ),
    BLOCK_IGNITING(
        "블럭 점화",
        OptionType.PERMISSION,
        "활성화 시 블럭 점화가 가능 합니다.\n비 활성화 시 블럭 점화가 불가능 합니다.",
        true
    ),
    BUCKET_FILL(
        "양동이 채우기",
        OptionType.PERMISSION,
        "활성화 시 양동이 채우기가 가능 합니다.\n비 활성화 시 양동이 채우기가 불가능 합니다.",
        true
    ),
    BUCKET_EMPTY(
        "양동이 비우기",
        OptionType.PERMISSION,
        "활성화 시 양동이 비우기가 가능 합니다.\n비 활성화 시 양동이 비우기가 불가능 합니다.",
        true
    ),
    HANGING_BREAK(
        "그림 및 액자 파괴",
        OptionType.PERMISSION,
        "활성화 시 그림 및 액자 파괴가 가능 합니다.\n비 활성화 시 그림 및 액자 파괴가 불가능 합니다.",
        true
    ),
    HANGING_PLACE(
        "그림 및 액자 설치",
        OptionType.PERMISSION,
        "활성화 시 그림 및 액자 설치가 가능 합니다.\n비 활성화 시 그림 및 액자 설치가 불가능 합니다.",
        true
    ),
    DROP_ITEM(
        "아이템 버리기",
        OptionType.PERMISSION,
        "활성화 시 아이템 버리기가 가능 합니다.\n비 활성화 시 아이템 버리기가 불가능 합니다.",
        true
    ),
    PICKUP_ITEM(
        "아이템 줍기",
        OptionType.PERMISSION,
        "활성화 시 아이템 줍기가 가능 합니다.\n비 활성화 시 아이템 줍기가 불가능 합니다.",
        true
    ),
    ARMOR_STAND_MANIPULATION(
        "아머스탠드 조작",
        OptionType.PERMISSION,
        "활성화 시 아머스탠드 조작이 가능 합니다.\n비 활성화 시 아머스탠드 조작이 불가능 합니다.",
        true
    ),

    DAMAGE(
        "데미지 입는 여부",
        OptionType.PROTECTION,
        "활성화 시 데미지 입는 여부이 보호가 됩니다.\n비 활성화 시 데미지 입는 여부가 보호가 되지 않습니다.",
        true
    ),
    POTION(
        "물약 사용 여부",
        OptionType.PROTECTION,
        "활성화 시 물약 사용 여부이 보호가 됩니다.\n비 활성화 시 물약 사용 여부가 보호가 되지 않습니다.",
        true
    ),
    FIRE(
        "불 연소 여부",
        OptionType.PROTECTION,
        "활성화 시 불 연소 여부이 보호가 됩니다.\n비 활성화 시 불 연소 여부가 보호가 되지 않습니다.",
        true
    ),
    FADE(
        "자연 환경에 따른 블럭 사라짐",
        OptionType.PROTECTION,
        "활성화 시 자연 환경에 따른 블럭 사라짐이 보호가 됩니다.\n비 활성화 시 자연 환경에 따른 블럭 사라짐가 보호가 되지 않습니다.",
        true
    ),
    GROWTH(
        "성장",
        OptionType.PROTECTION,
        "활성화 시 성장이 보호가 됩니다.\n비 활성화 시 성장가 보호가 되지 않습니다.",
        true
    ),
    FORM(
        "자연 환경에 따른 블럭 쌓임",
        OptionType.PROTECTION,
        "활성화 시 자연 환경에 따른 블럭 쌓임이 보호가 됩니다.\n비 활성화 시 자연 환경에 따른 블럭 쌓임가 보호가 되지 않습니다.",
        true
    ),
    SPREAD(
        "퍼짐",
        OptionType.PROTECTION,
        "활성화 시 퍼짐이 보호가 됩니다.\n비 활성화 시 퍼짐가 보호가 되지 않습니다.",
        true
    ),
    REDSTONE(
        "레드스톤",
        OptionType.PROTECTION,
        "활성화 시 레드스톤이 보호가 됩니다.\n비 활성화 시 레드스톤가 보호가 되지 않습니다.",
        true
    ),
    PISTON(
        "피스톤",
        OptionType.PROTECTION,
        "활성화 시 피스톤이 보호가 됩니다.\n비 활성화 시 피스톤가 보호가 되지 않습니다.",
        true
    ),
    DISPENSER(
        "디스펜서",
        OptionType.PROTECTION,
        "활성화 시 디스펜서이 보호가 됩니다.\n비 활성화 시 디스펜서가 보호가 되지 않습니다.",
        true
    ),
    OVERFLOW(
        "범위를 넘어선 성장",
        OptionType.PROTECTION,
        "활성화 시 범위를 넘어선 성장이 보호가 됩니다.\n비 활성화 시 범위를 넘어선 성장가 보호가 되지 않습니다.",
        true
    ),
    FLOOD(
        "액체 흐름",
        OptionType.PROTECTION,
        "활성화 시 액체 흐름이 보호가 됩니다.\n비 활성화 시 액체 흐름가 보호가 되지 않습니다.",
        true
    ),
    ENTITY_PICKUP_ITEM(
        "엔티티가 아이템 줍기",
        OptionType.PROTECTION,
        "활성화 시 엔티티가 아이템 줍기이 보호가 됩니다.\n비 활성화 시 엔티티가 아이템 줍기가 보호가 되지 않습니다.",
        true
    ),
    ENTITY_CHANGE_BLOCK(
        "엔티티가 블럭 변경",
        OptionType.PROTECTION,
        "활성화 시 엔티티가 블럭 변경이 보호가 됩니다.\n비 활성화 시 엔티티가 블럭 변경가 보호가 되지 않습니다.",
        true
    ),
    EXPLOSION(
        "폭발",
        OptionType.PROTECTION,
        "활성화 시 폭발이 보호가 됩니다.\n비 활성화 시 폭발가 보호가 되지 않습니다.",
        true
    ),
    ITEM_TRANSFER(
        "아이템 교환",
        OptionType.PROTECTION,
        "활성화 시 아이템 교환이 보호가 됩니다.\n비 활성화 시 아이템 교환가 보호가 되지 않습니다.",
        true
    ),
    VEHICLE_ENTRANCE(
        "탈것으로 입장",
        OptionType.PROTECTION,
        "활성화 시 탈것으로 입장이 보호가 됩니다.\n비 활성화 시 탈것으로 입장가 보호가 되지 않습니다.",
        true
    ),
    VEHICLE_EXIT(
        "탈것으로 퇴장",
        OptionType.PROTECTION,
        "활성화 시 탈것으로 퇴장이 보호가 됩니다.\n비 활성화 시 탈것으로 퇴장가 보호가 되지 않습니다.",
        true
    ),
    SPONGE(
        "스펀지",
        OptionType.PROTECTION,
        "sponge",
        true
    );

    fun toItemStack(value: Boolean): ItemStack {
        return ItemStackFactory()
            .setType(if (value) Material.GREEN_WOOL else Material.RED_WOOL)
            .setDisplayName("&f$korName".replaceChatColorCode())
            .addLore("&f$description".replaceChatColorCode())
            .build()
    }

    fun getPublicValue(): Boolean {
        return when (optionType) {
            OptionType.PERMISSION -> true
            OptionType.PROTECTION -> false
        }
    }

    companion object {
        fun getDefaultTable() =
            values().associate { it to it.defaultValue } as MutableMap<LandOption, Boolean>
    }
}
