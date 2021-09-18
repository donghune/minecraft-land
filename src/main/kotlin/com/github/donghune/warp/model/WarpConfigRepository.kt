package com.github.donghune.warp.model

import com.github.donghune.LandPlugin
import com.github.donghune.namulibrary.extension.emptyLocation
import com.github.donghune.namulibrary.model.ConfigRepository
import org.bukkit.Material
import java.io.File

object WarpConfigRepository : ConfigRepository<WarpConfig>() {

    override val dataType: Class<WarpConfig> = WarpConfig::class.java
    override val file: File = LandPlugin.warpPlugin.dataFolder

    override fun getDefaultData(): WarpConfig {
        return WarpConfig(
            listOf(
                WarpPoint(
                    "example",
                    emptyLocation(),
                    Material.DIRT,
                    "DIRT",
                    listOf("is real dirt!")
                )
            )
        )
    }
}
