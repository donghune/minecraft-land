package com.github.donghune.land.model.land.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.util.BoundingBox
import java.util.*

@SerializableAs("Land")
data class Land (
    val name: String,
    val boundingBox: BoundingBox,
    var type: LandType,
    var owner: UUID,
    val member: MutableList<UUID>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Land {
            return Land(
                data["name"] as String,
                data["boundingBox"] as BoundingBox,
                data["type"] as LandType,
                data["owner"] as UUID,
                (data["member"] as List<String>).map { UUID.fromString(it) }.toMutableList(),
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "boundingBox" to boundingBox,
            "type" to type,
            "owner" to owner,
            "member" to member.map { it.toString() }.toList(),
        )
    }

}