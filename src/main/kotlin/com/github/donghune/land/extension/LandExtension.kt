package com.github.donghune.land.extension

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import java.util.*

fun Land.getVillage(): Village? {
    return VillageRepository.getList()
        .find { it.uuid == this.owner }
}

fun Land.getNation(): Nation? {
    return NationRepository.getList()
        .find { it.uuid == this.owner }
}

fun UUID.getVillage(): Village? {
    return VillageRepository.get(this)
}

fun UUID.getNation(): Nation? {
    return NationRepository.get(this)
}
