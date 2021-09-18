package com.github.donghune.rating.model.repository

import com.github.donghune.namulibrary.model.ConfigRepository
import com.github.donghune.plugin
import com.github.donghune.rating.model.entity.CreditRating
import com.github.donghune.rating.model.entity.CreditRatingConfig
import java.io.File

object CreditRatingConfigRepository : ConfigRepository<CreditRatingConfig>() {
    override val dataType: Class<CreditRatingConfig> = CreditRatingConfig::class.java
    override val file: File = File(plugin.dataFolder.path + "/credit_rating")

    override fun getDefaultData(): CreditRatingConfig {
        return CreditRatingConfig(
            listOf(
                CreditRating(0, 14, "F", 2988000),
                CreditRating(1, 14, "FF", 2988000),
                CreditRating(2, 14, "FFF", 2988000),
                CreditRating(3, 13, "E", 2988000),
                CreditRating(4, 13, "EE", 2988000),
                CreditRating(5, 13, "EEE", 2988000),
                CreditRating(6, 12, "D-", 2988000),
                CreditRating(7, 12, "D", 2988000),
                CreditRating(8, 12, "D+", 2988000),
                CreditRating(9, 11, "DD-", 2988000),
                CreditRating(10, 11, "DD", 2988000),
                CreditRating(11, 11, "DD+", 2988000),
                CreditRating(12, 10, "DDD-", 2988000),
                CreditRating(13, 10, "DDD", 2988000),
                CreditRating(14, 10, "DDD+", 2988000),
                CreditRating(15, 9, "C-", 2988000),
                CreditRating(16, 9, "C", 2988000),
                CreditRating(17, 9, "C+", 2988000),
                CreditRating(18, 8, "CC-", 2988000),
                CreditRating(19, 8, "CC", 2988000),
                CreditRating(20, 8, "CC+", 2988000),
                CreditRating(21, 7, "CCC-", 2988000),
                CreditRating(22, 7, "CCC", 2988000),
                CreditRating(23, 7, "CCC+", 2988000),
                CreditRating(24, 6, "B-", 2988000),
                CreditRating(25, 6, "B", 2988000),
                CreditRating(26, 6, "B+", 2988000),
                CreditRating(27, 5, "BB-", 2988000),
                CreditRating(28, 5, "BB", 2988000),
                CreditRating(29, 5, "BB+", 2988000),
                CreditRating(30, 4, "BBB-", 2988000),
                CreditRating(31, 4, "BBB", 2988000),
                CreditRating(32, 4, "BBB+", 2988000),
                CreditRating(33, 3, "A-", 2988000),
                CreditRating(34, 3, "A", 2988000),
                CreditRating(35, 3, "A+", 2988000),
                CreditRating(36, 2, "AA-", 2988000),
                CreditRating(37, 2, "AA", 2988000),
                CreditRating(38, 2, "AA+", 2988000),
                CreditRating(39, 1, "AAA-", 2988000),
                CreditRating(40, 1, "AAA", 2988000),
                CreditRating(41, 1, "AAA+", 2988000),
            )
        )
    }
}
