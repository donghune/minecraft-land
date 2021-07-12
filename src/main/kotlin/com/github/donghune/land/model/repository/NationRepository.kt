package com.github.donghune.land.model.repository

import com.github.donghune.land.model.entity.Nation
import com.github.donghune.plugin
import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File
import java.util.*

object NationRepository : EntityRepository<Nation>() {

    override val dataType: Class<Nation> = Nation::class.java
    override val file: File = File(plugin.dataFolder.path + "/lands")

    override fun getDefaultData(key: String): Nation {
        return Nation(UUID.randomUUID().toString(), key, UUID.randomUUID().toString(), listOf())
    }
}