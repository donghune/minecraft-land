package com.github.donghune.land.model.entity

import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class LandOption(
    val korName: String,
    val description: String,
    val defaultValue: Boolean,
) {

    BUILD(
        "build",
        "건축 권한",
        false
    ),
    PVP(
        "pvp",
        "PVP 권한",
        false
    ),
    CHEST_ACCESS(
        "chest access",
        "보관 기능을 가진 블록의 사용 가능 권한",
        false
    ),
    USE(
        "use",
        "레버,인첸트 테이블,문 등 기능을 가진 블록의 사용 가능 권한",
        false
    ),
    VEHICLE_PLACE(
        "vehicle place",
        "운송 수단의 설치 권한",
        false
    ),
    VEHICLE_DESTROY(
        "vehicle destroy",
        "운송 수단의 아이템화 가능 권한",
        false
    ),
    ENDERMAN_DRIEF(
        "enderman drief",
        "엔더맨이 블록을 옮기는 행위 가능 권한",
        false
    ),
    LAND_ENTER(
        "greeting",
        "토지에 입장했을 때 채팅창에 표시될 메세지",
        false
    ),
    LAND_EXIT(
        "farewell",
        "토지에 퇴장했을 때 채팅창에 표시될 메세지",
        false
    ),
    ENTRY(
        "entry",
        "입장 가능 권한",
        false
    ),
    ENTITY_ITEM_FRAME_DESTORY(
        "entity item frame destory",
        "아이템 프레임 파괴 권한",
        false
    ),
    SNOW_MELT(
        "snow melt",
        "눈이 녹는 것에 대한 여부",
        true
    ),
    ICE_FORM(
        "ice form",
        "얼음이 어는 것에 대한 여부",
        true
    ),
    ICE_MELT(
        "ice melt",
        "얼음이 녹는 것에 대한 여부",
        true
    ),
    LEAF_DECAY(
        "leaf decay",
        "나뭇잎 블록이 사라지는 것이 대한 여부",
        true
    ),
    GRASS_GROWTH(
        "grass growth",
        "잔디가 퍼지는 것에 대한 여부",
        true
    ),
    FIRE_SPREAD(
        "fire spread",
        "불이 번지는 것에 대한 여부",
        false
    ),
    LAVA_FLOW(
        "lava flow",
        "암이 흐르는 것에 대한 여부",
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