package com.github.donghune.deprecated

import com.github.donghune.util.toBlockArray
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.util.BoundingBox

@SerializableAs("NBox")
data class NBox(
    val name: String,
    val pos1: Location,
    val pos2: Location
) : ConfigurationSerializable {
    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): NBox {
            return NBox(
                data["name"] as String,
                data["pos1"] as Location,
                data["pos2"] as Location
            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "pos1" to pos1,
            "pos2" to pos2
        )
    }

    fun toBoundingBox() = BoundingBox(
        pos1.x,
        pos1.y,
        pos1.z,
        pos2.x,
        pos2.y,
        pos2.z,
    )

    private val minX: Int = pos1.blockX.coerceAtMost(pos2.blockX)
    private val maxX: Int = pos1.blockX.coerceAtLeast(pos2.blockX)

    private val minY: Int = pos1.blockY.coerceAtMost(pos2.blockY)
    private val maxY: Int = pos1.blockY.coerceAtLeast(pos2.blockY)

    private val minZ: Int = pos1.blockZ.coerceAtMost(pos2.blockZ)
    private val maxZ: Int = pos1.blockZ.coerceAtLeast(pos2.blockZ)

    fun inRange(location: Location): Boolean {
        return location.blockX in minX..maxX && location.blockY in minY..maxY && location.blockZ in minZ..maxZ
    }

    val blockArray: List<Block>
        get() = _blockArray
    private var _blockArray: MutableList<Block> = mutableListOf()

    init {
        _blockArray = (pos1 to pos2).toBlockArray() as MutableList<Block>
    }
}
