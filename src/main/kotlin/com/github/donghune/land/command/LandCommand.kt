package com.github.donghune.land.command

import com.github.donghune.land.extension.getBelongingNation
import com.github.donghune.land.extension.getBelongingVillage
import com.github.donghune.land.inventory.LandInventory
import com.github.donghune.land.inventory.group.LandGroupBuildJoinInventory
import com.github.donghune.land.inventory.real.GroupSettingInventory
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.usecase.BuyLandUseCase
import com.github.donghune.land.model.usecase.BuyLandUseCaseParam
import com.github.donghune.plugin
import com.github.monun.kommand.kommand
import org.bukkit.entity.Player

object LandCommand {
    fun initialize() {
        plugin.kommand {
            register("village") {
                executes {
                    val player = it.sender as Player
                    val village = player.getBelongingVillage()
                    if (village == null) {
                        LandGroupBuildJoinInventory(LandType.VILLAGE).open(player)
                        return@executes
                    }
                    GroupSettingInventory(village).open(player)
                }
            }
            register("nation") {
                executes {
                    val player = it.sender as Player
                    val nation = player.getBelongingNation()
                    if (nation == null) {
                        LandGroupBuildJoinInventory(LandType.NATION).open(player)
                        return@executes
                    }
                    GroupSettingInventory(nation).open(player)
                }
            }
            register("land") {
                executes {
                    val player = it.sender as Player
                    LandInventory().open(player)
                }
            }
            register("landa") {
                then("buy") {
                    executes {
                        val player = it.sender as Player

                        BuyLandUseCase(BuyLandUseCaseParam(player, LandType.PERSONAL))
                    }
                }
            }
        }
    }
}
