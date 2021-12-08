package com.github.donghune.land.model.entity

import com.github.donghune.land.extension.*
import com.github.donghune.land.model.repository.*
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

@SerializableAs("Land")
data class Land(
    val chunkKey: Long,
    var type: LandType,
    var owner: String,
    var landOption: MutableMap<LandOption, Boolean>,
) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Land {
            return try {
                Land(
                    (data["chunkKey"] as String).toLong(),
                    LandType.valueOf(data["type"] as String),
                    data["owner"] as String,
                    (data["landOption"] as Map<String, Boolean>)
                        .mapKeys { LandOption.valueOf(it.key) }
                        .toMap() as MutableMap<LandOption, Boolean>
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                LandRepository.getDefaultData((data["chunkKey"] as String))
            }
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "chunkKey" to chunkKey.toString(),
            "type" to type.toString(),
            "owner" to owner,
            "landOption" to landOption.mapKeys { it.key.toString() },
        )
    }

    fun toItemStack(index: Int): ItemStack {
        val chunk = getChunk()
        return ItemStackFactory()
            .setType(Material.GRASS_BLOCK)
            .setDisplayName("&f${index}번 토지".replaceChatColorCode())
            .addLore("위치 X[%d] Z[%d]".format(chunk.x, chunk.z).replaceChatColorCode())
            .build()
    }
}

/**
 * 토지 권한
 */
fun Land?.hasPermission(entity: Entity, landOption: LandOption): Boolean {

    // Permission 기본적으로 공공재의 권한은 모두 가지고 있음 그래서 true 로 나와야 함
    if (this == null || this.type == LandType.NONE) {
        return landOption.getPublicValue()
    }

    // 개인 토지인 경우 본인 만 가능 하거나 옵션에 따라 다름
    if (type == LandType.PERSONAL) {
        return owner == entity.uniqueId.toString() || this.landOption[landOption] ?: landOption.defaultValue
    }

    // 그룹 토지인 경우
    if (type == LandType.VILLAGE || type == LandType.NATION) {
        val group = GroupRepository.getGroup(this.owner) ?: throw Exception("fucking")
        return group.getMembers()
            .contains(entity.uniqueId.toString()) || this.landOption[landOption] ?: landOption.defaultValue
    }

    return true
}

/**
 *  토지 보호 건
 */
fun Land?.hasProtection(landOption: LandOption): Boolean {

    // Protection 기본적으로 공공재는 보호를 하지 않음 그래서 false 로 나와야 함
    if (this == null) {
        return landOption.getPublicValue()
    }

    return this@hasProtection.landOption[landOption] ?: landOption.defaultValue
}
