package com.github.donghune.land.extension

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.repository.LandRepository
import org.bukkit.Bukkit
import org.bukkit.Chunk

fun Land.getChunk(): Chunk = Bukkit.getWorld("world")!!.getChunkAt(chunkKey)

fun Chunk.getLand(): Land? = LandRepository.get(chunkKey.toString())
