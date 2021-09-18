package com.github.donghune.shop.command

import com.github.donghune.shop.model.entity.Shop
import com.github.donghune.shop.model.repo.ShopRepository
import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.suggest

object ShopArgument : KommandArgument<Shop> {

    override val parseFailMessage: String
        get() = "${KommandArgument.TOKEN} <-- 해당 상점를 찾지 못했습니다."

    override fun parse(context: KommandContext, param: String): Shop? {
        return ShopRepository.get(param)
    }

    override fun suggest(context: KommandContext, target: String): Collection<String> {
        return ShopRepository.getList().suggest(target) { it.name }
    }
}
