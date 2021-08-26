package com.github.donghune.land.model.repository

import com.github.donghune.land.model.entity.Village
import com.github.donghune.plugin
import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File
import java.util.*

object VillageRepository : EntityRepository<Village>() {

    override val dataType: Class<Village> = Village::class.java
    override val file: File = File(plugin.dataFolder.path + "/villages")

    override fun getDefaultData(key: String): Village {
        return Village(UUID.randomUUID().toString(), key, UUID.randomUUID().toString(), mutableListOf(), 0, 0)
    }

    fun get(uuid: UUID) = get(uuid.toString())
}