package com.github.donghune.land.command

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.repository.LandRepository
import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.suggest

object LandArgument : KommandArgument<Land> {

    override val parseFailMessage: String
        get() = "${KommandArgument.TOKEN} <-- 해당 토지를 찾지 못했습니다."

    override fun parse(context: KommandContext, param: String): Land? {
        return LandRepository.get(param)
    }

    override fun suggest(context: KommandContext, target: String): Collection<String> {
        return LandRepository.getList().suggest(target) { it.chunkKey.toString() }
    }
}
