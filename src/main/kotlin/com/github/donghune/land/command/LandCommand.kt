package com.github.donghune.land.command

import com.github.donghune.land.inventory.LandMainInventory
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.usecase.BuyLandUseCase
import com.github.donghune.plugin
import com.github.monun.kommand.kommand
import org.bukkit.entity.Player

object LandCommand {
    fun initialize() {
        plugin.kommand {
            register("land") {
                executes {
                    val player = it.sender as Player
                    LandMainInventory().open(player)
                }
            }
        }
        plugin.kommand {
            register("landa") {
                then("buy") {
                    executes {
                        val player = it.sender as Player

                        BuyLandUseCase.buyChunk(player, LandType.PERSONAL)
                    }
                }
            }
        }
    }
}
