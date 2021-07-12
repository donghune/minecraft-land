package com.github.donghune.rating.model.repository

import com.github.donghune.rating.model.entity.PlayerCreditRating
import com.github.donghune.plugin
import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File
import java.util.*

object  PlayerCreditRatingRepository : EntityRepository<PlayerCreditRating>() {

    override val dataType: Class<PlayerCreditRating> = PlayerCreditRating::class.java
    override val file: File = File(plugin.dataFolder.path + "/credit_rating/players")

    override fun getDefaultData(key: String): PlayerCreditRating {
        return PlayerCreditRating(
            UUID.fromString(key),
            0
        )
    }
}