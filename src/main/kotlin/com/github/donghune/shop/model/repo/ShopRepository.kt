package com.github.donghune.shop.model.repo

import com.github.donghune.LandPlugin
import com.github.donghune.namulibrary.model.EntityRepository
import com.github.donghune.shop.model.entity.Shop
import java.io.File

object ShopRepository : EntityRepository<Shop>() {
    override val file: File = File("${LandPlugin.shopPlugin.dataFolder}/shops")
    override val dataType: Class<Shop>
        get() = Shop::class.java
    override fun getDefaultData(key: String): Shop {
        return Shop(key, mutableListOf(), "")
    }
}
