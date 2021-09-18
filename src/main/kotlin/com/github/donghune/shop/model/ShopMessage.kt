package com.github.donghune.shop.model

object ShopMessage {

    const val UNKNOWN_NAME = "존재하지 않는 상점입니다."
    const val ALREADY_SHOP = "이미 존재하는 상점입니다."

    const val CREATE_SHOP = "상점을 생성하였습니다"
    const val DELETE_SHOP = "상점을 삭제하였습니다"

    const val ADD_SHOP_STUFF = "상점에 아이템을 추가하였습니다."
    const val REMOVE_SHOP_STUFF = "상점에 아이템을 삭제하였습니다."

    const val ADD_KEYWORD_SHOP_STUFF = "키워드 상점에 아이템을 추가하였습니다."
    const val REMOVE_KEYWORD_SHOP_STUFF = "키워드 상점에 아이템을 삭제하였습니다."

    const val BUY_ITEM = "아이템을 구매하였습니다."
    const val SELL_ITEM = "아이템을 판매하였습니다. ( %s * %d = %s )"
    const val CAN_NOT_SELL_ITEM = "판매할 수 없는 아이템 입니다."

    const val SET_SHOP_NPC = "상점 NPC를 설정하였습니다."
}
