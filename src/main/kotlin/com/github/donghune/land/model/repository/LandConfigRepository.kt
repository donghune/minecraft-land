package com.github.donghune.land.model.repository

import com.github.donghune.land.model.entity.LandConfig
import com.github.donghune.namulibrary.model.ConfigRepository
import com.github.donghune.plugin
import java.io.File

object LandConfigRepository : ConfigRepository<LandConfig>() {
    override val dataType: Class<LandConfig> = LandConfig::class.java
    override val file: File = File(plugin.dataFolder,"LandConfig.yml")

    override fun getDefaultData(): LandConfig {
        return LandConfig(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        )
    }
}