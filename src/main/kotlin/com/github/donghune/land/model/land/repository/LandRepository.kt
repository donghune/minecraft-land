package com.github.donghune.land.model.land.repository

import com.github.donghune.land.model.land.entity.Land
import com.github.donghune.land.model.land.entity.LandType
import com.github.donghune.land.plugin
import com.github.donghune.namulibrary.model.EntityRepository
import org.bukkit.util.BoundingBox
import java.io.File
import java.util.*

object LandRepository : EntityRepository<Land>() {

    override val dataType: Class<Land> = Land::class.java
    override val file: File = File(plugin.dataFolder.path + "/lands")

    override fun getDefaultData(key: String): Land {
        return Land(key, BoundingBox(), LandType.NONE, UUID.randomUUID(), mutableListOf())
    }
}