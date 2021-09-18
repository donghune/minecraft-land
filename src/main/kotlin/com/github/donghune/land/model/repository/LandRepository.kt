package com.github.donghune.land.model.repository

import com.github.donghune.land.model.entity.Land
import com.github.donghune.namulibrary.model.EntityRepository
import com.github.donghune.plugin
import java.io.File

object LandRepository : EntityRepository<Land>() {

    override val dataType: Class<Land> = Land::class.java
    override val file: File = File(plugin.dataFolder.path + "/lands")

    override fun getDefaultData(key: String): Land {
        throw Exception("This function is not available.")
    }
}
