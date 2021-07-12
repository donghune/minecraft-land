package com.github.donghune.land.model.land.repository

import com.github.donghune.land.model.land.entity.Village
import com.github.donghune.land.plugin
import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File
import java.util.*

object VillageRepository : EntityRepository<Village>() {

    override val dataType: Class<Village> = Village::class.java
    override val file: File = File(plugin.dataFolder.path + "/lands")

    override fun getDefaultData(key: String): Village {
        return Village(UUID.randomUUID().toString(), key, UUID.randomUUID().toString(), listOf())
    }
}