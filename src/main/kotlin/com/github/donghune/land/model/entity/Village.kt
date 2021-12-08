package com.github.donghune.land.model.entity

import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.rating.model.extension.getCreditRating
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack
import java.util.*

@SerializableAs("Village")
class Village(
    uuid: String,
    name: String,
    owner: String,
    member: MutableList<String>,
    vaultLevel: Int,
    vaultGold: Long,
    createAt: Date,
) : Group(uuid, name, owner, member, vaultLevel, vaultGold, createAt), ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Village {
            return Village(
                data["uuid"] as String,
                data["name"] as String,
                data["owner"] as String,
                data["member"] as MutableList<String>,
                data["vaultLevel"] as Int,
                (data["vaultGold"] as String).toLong(),
                Date((data["createdAt"] as String).toLong()),
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid,
            "name" to name,
            "owner" to owner,
            "member" to child,
            "vaultLevel" to vaultLevel,
            "vaultGold" to vaultGold.toString(),
            "createdAt" to createdAt.time.toString()
        )
    }

    fun toItemStack(): ItemStack = ItemStackFactory()
        .setType(Material.PLAYER_HEAD)
        .SkullMeta { owningPlayer = Bukkit.getOfflinePlayer(this@Village.owner.toUUID()) }
        .setDisplayName("&f${Bukkit.getOfflinePlayer(this@Village.owner.toUUID()).name}님의 마을".replaceChatColorCode())
        .setLore(
            child.map {
                "&f- ${Bukkit.getOfflinePlayer(it.toUUID()).name}"
            }.toList()
        )
        .addLore("")
        .addLore("소속되어 있는 국가 : ${NationRepository.getList().find { it.child.contains(uuid) }?.name ?: "없음"}")
        .build()

    override fun getRating(): Int {
        return child.map { uuid: String -> Bukkit.getOfflinePlayer(uuid.toUUID()) }
            .map { offlinePlayer: OfflinePlayer -> offlinePlayer.getCreditRating().getCreditRating().rating }
            .average()
            .toInt()
    }

    override fun getType(): LandType {
        return LandType.VILLAGE
    }

    override fun getMembers(): List<String> {
        return child
    }

    override fun getLandList(): List<Land> {
        return LandRepository.getList()
            .filter { it.owner == uuid }
            .filter { it.type == LandType.VILLAGE }
    }
}
