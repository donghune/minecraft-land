package com.github.donghune.regenblock.model

import com.github.donghune.LandPlugin
import com.github.donghune.namulibrary.model.EntityRepository
import org.bukkit.Material
import java.io.File

object RegenRegionInfoRepository : EntityRepository<RegenRegionInfo>() {
    override val dataType: Class<RegenRegionInfo> = RegenRegionInfo::class.java
    override val file: File = File(LandPlugin.regenBlockPlugin.dataFolder.path + "/region-info")

    override fun getDefaultData(key: String): RegenRegionInfo {
        return RegenRegionInfo(
            name = "example",
            regenDelay = 0,
            mapOf(
                Material.DIRT to 1
            )
        )
    }
}
