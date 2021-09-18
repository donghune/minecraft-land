package com.github.donghune.regenblock.model

import com.github.donghune.namulibrary.model.EntityRepository
import org.bukkit.util.BoundingBox
import java.io.File

object RegenRegionRepository : EntityRepository<RegenRegion>() {
    override val dataType: Class<RegenRegion> = RegenRegion::class.java
    override val file: File = File(com.github.donghune.LandPlugin.regenBlockPlugin.dataFolder.path + "/regions")

    override fun getDefaultData(key: String): RegenRegion {
        return RegenRegion(
            name = key,
            area = BoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            regenRegionInfo = RegenRegionInfo("example", 1, mapOf())
        )
    }
}
