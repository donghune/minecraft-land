package com.github.donghune.land.model.repository

import com.github.donghune.land.model.entity.Group
import java.util.*

object GroupRepository {

    fun getGroupList(): List<Group> {
        return (VillageRepository.getList() intersect NationRepository.getList()).toList()
    }

    fun getGroup(uuid: UUID): Group? {
        return (VillageRepository.getList() intersect NationRepository.getList()).find { it.uuid == uuid.toString() }
    }

    fun getGroup(uuid: String): Group? {
        return (VillageRepository.getList() intersect NationRepository.getList()).find { it.uuid == uuid }
    }

    fun getGroupByOwner(uuid: String): Group? {
        return (VillageRepository.getList() intersect NationRepository.getList()).find { it.owner == uuid }
    }
}
