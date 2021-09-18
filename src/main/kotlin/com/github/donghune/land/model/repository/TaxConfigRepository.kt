package com.github.donghune.land.model.repository

import com.github.donghune.land.model.entity.TaxConfig
import com.github.donghune.namulibrary.model.ConfigRepository
import com.github.donghune.plugin
import java.io.File

object TaxConfigRepository : ConfigRepository<TaxConfig>() {
    override val dataType: Class<TaxConfig> = TaxConfig::class.java
    override val file: File = File(plugin.dataFolder, "TaxConfig")

    override fun getDefaultData(): TaxConfig {
        return TaxConfig(
            1.5,
            1.5,
            mapOf(
                1 to 1494,
                2 to 2988,
                3 to 5976,
                4 to 8964,
                5 to 11952,
                6 to 14940,
                7 to 22410,
                8 to 31374,
                9 to 41832,
                10 to 53784,
                11 to 74700,
                12 to 101592,
                13 to 131472,
                14 to 149400,
            ),
            mapOf(
                1 to 2241,
                2 to 4482,
                3 to 8964,
                4 to 13446,
                5 to 17928,
                6 to 22410,
                7 to 33615,
                8 to 47061,
                9 to 62748,
                10 to 80676,
                11 to 112050,
                12 to 152388,
                13 to 197208,
                14 to 224100,
            ),
        )
    }
}
