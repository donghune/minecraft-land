package com.github.donghune.money.model

import com.github.donghune.money.model.entity.PlayerMoney
import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File

object PlayerMoneyRepository : EntityRepository<PlayerMoney>() {
    override val file: File = File(com.github.donghune.LandPlugin.moneyPlugin.dataFolder.path + "/players")
    override val dataType: Class<PlayerMoney> = PlayerMoney::class.java
    override fun getDefaultData(key: String): PlayerMoney {
        return PlayerMoney(key, 30000)
    }
}
