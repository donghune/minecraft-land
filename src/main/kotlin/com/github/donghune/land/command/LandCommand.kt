package com.github.donghune.land.command

import com.github.donghune.land.extension.getSelection
import com.github.donghune.land.inventory.LandMainInventory
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.land.model.usecase.BuyLandUseCase
import com.github.donghune.plugin
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.argument.string
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
                then("create") {
                    then("name" to string()) {
                        executes {
                            val player = it.sender as Player
                            val name = it.parseArgument<String>("name")

                            if (LandRepository.get(name) != null) {
                                player.sendErrorMessage("이미 존재하는 땅 이름 입니다.")
                                return@executes
                            }

                            LandRepository.create(name, Land(
                                name,
                                player.getSelection(),
                                LandType.NONE,
                                "",
                                mutableListOf()
                            ))
                            player.sendInfoMessage("$name 땅을 생성하였습니다.")
                        }
                    }
                }
                then("buy") {
                    then("land" to LandArgument) {
                        executes {
                            val player = it.sender as Player
                            val land = it.parseArgument<Land>("land")

                            BuyLandUseCase.buyLand(player, land, LandType.PRIVATE)
                        }
                    }
                }
            }
        }
    }
}
