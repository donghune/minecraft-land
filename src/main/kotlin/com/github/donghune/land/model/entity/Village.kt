package com.github.donghune.land.model.entity

import com.github.donghune.land.extension.toOfflinePlayer
import com.github.donghune.land.extension.toPlayer
import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@SerializableAs("Village")
class Village(
    uuid: String,
    name: String,
    owner: String,
    member: MutableList<String>,
    vaultLevel: Int,
    vaultGold: Int,
) : Group(uuid, name, owner, member, vaultLevel, vaultGold), ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Village {
            return Village(
                data["uuid"] as String,
                data["name"] as String,
                data["owner"] as String,
                data["member"] as MutableList<String>,
                data["vaultLevel"] as Int,
                data["vaultGold"] as Int,
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid,
            "name" to name,
            "owner" to owner,
            "member" to member,
            "vaultLevel" to vaultLevel,
            "vaultGold" to vaultGold,
        )
    }

    fun getOwnerPlayer() : OfflinePlayer {
        return owner.toUUID().toOfflinePlayer()
    }

    fun toItemStack(): ItemStack = ItemStackFactory()
        .setType(Material.PLAYER_HEAD)
        .SkullMeta { owningPlayer = Bukkit.getOfflinePlayer(this@Village.owner.toUUID()) }
        .setDisplayName("&f${Bukkit.getOfflinePlayer(this@Village.owner.toUUID()).name}님의 마을".replaceChatColorCode())
        .setLore(
            member.map {
                "&f- ${Bukkit.getOfflinePlayer(it.toUUID()).name}"
            }.toList()
        )
        .addLore("")
        .addLore("소속되어 있는 국가 : ${NationRepository.getList().find { it.member.contains(uuid) }?.name ?: "없음"}")
        .build()

}