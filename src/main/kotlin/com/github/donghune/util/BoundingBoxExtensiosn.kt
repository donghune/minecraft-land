package com.github.donghune.util

import org.bukkit.Location
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

fun BoundingBox.contains2(x: Double, y: Double, z: Double): Boolean {
    return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ
}

fun BoundingBox.contains2(position : Vector): Boolean {
    return this.contains2(position.x, position.y, position.z)
}