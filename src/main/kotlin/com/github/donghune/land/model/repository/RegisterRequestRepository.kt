package com.github.donghune.land.model.repository

import com.github.donghune.land.model.entity.RegisterRequest
import com.github.donghune.namulibrary.model.EntityRepository
import com.github.donghune.plugin
import java.io.File
import java.util.*

object RegisterRequestRepository : EntityRepository<RegisterRequest>() {
    override val dataType: Class<RegisterRequest> = RegisterRequest::class.java
    override val file: File = File(plugin.dataFolder, "register_requests")

    fun getGroupUUID(childUUID: UUID): RegisterRequest? {
        return get(childUUID.toString())
    }

    fun request(childUUID: UUID, groupUUID: UUID) {
        create(childUUID.toString(), RegisterRequest(childUUID.toString(), groupUUID.toString()))
    }

    fun accept(childUUID: UUID) {
        remove(childUUID.toString())
    }

    fun decline(childUUID: UUID) {
        remove(childUUID.toString())
    }

    override fun getDefaultData(key: String): RegisterRequest {
        return RegisterRequest(
            "", ""
        )
    }
}