package com.github.donghune.deprecated

import com.github.donghune.namulibrary.extension.emptyLocation
import com.github.donghune.namulibrary.model.EntityRepository
import com.github.donghune.plugin
import java.io.File

object NBoxRepository : EntityRepository<NBox>() {
    override val dataType: Class<NBox> = NBox::class.java
    override val file: File = File(plugin.dataFolder.path + "/nBox")

    override fun getDefaultData(key: String): NBox {
        return NBox(
            key,
            emptyLocation(),
            emptyLocation()
        )
    }
}
