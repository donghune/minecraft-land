package com.github.donghune.land.model.repository

import com.github.donghune.land.model.abstract.SingleDataRepository
import com.github.donghune.land.model.entity.PlayTimeConfig
import com.github.donghune.plugin
import java.io.File

object PlayTimeConfigRepository : SingleDataRepository<PlayTimeConfig>() {
    override val dataType: Class<PlayTimeConfig> = PlayTimeConfig::class.java
    override val file: File = File(plugin.dataFolder, "PlayTimeConfig")

    override fun getDefaultData(): PlayTimeConfig {
        return PlayTimeConfig(
            0,
            mutableMapOf(),
        )
    }
}
