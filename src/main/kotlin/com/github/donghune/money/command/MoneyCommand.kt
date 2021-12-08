package com.github.donghune.money.command

import com.github.donghune.hmm.wallet
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.plugin
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.player
import com.github.monun.kommand.kommand
import org.bukkit.entity.Player

object MoneyCommand {
    fun initialize() {
        plugin.kommand {
            register("money") {
                then("view") {
                    require { it is Player }
                    executes {
                        val player = it.sender as Player

                        it.sender.sendMessage("[System] 현재 보유중인 금액은 ${player.wallet.getMoney().toMoneyFormat()} 입니다.")
                    }
                }
            }
            register("moneya") {
                require { it.isOp }
                then("give") {
                    then("player" to player()) {
                        then("money" to integer()) {
                            executes {
                                val target = it.parseArgument<Player>("player")
                                val money = it.parseArgument<Int>("money")

                                target.wallet.giveMoney(money)
                                it.sender.sendMessage("[System] 해당 플레이어에게 돈을 지급 하였습니다.")
                            }
                        }
                    }
                }
                then("take") {
                    then("player" to player()) {
                        then("money" to integer()) {
                            executes {
                                val target = it.parseArgument<Player>("player")
                                val money = it.parseArgument<Int>("money")

                                target.wallet.takeMoney(money)
                                it.sender.sendMessage("[System] 해당 플레이어의 돈을 차감 하였습니다.")
                            }
                        }
                    }
                }
                then("set") {
                    then("player" to player()) {
                        then("money" to integer()) {
                            executes {
                                val target = it.parseArgument<Player>("player")
                                val money = it.parseArgument<Int>("money")

                                target.wallet.setMoney(money)
                                it.sender.sendMessage("[System] 해당 플레이어의 돈을 설정 하였습니다.")
                            }
                        }
                    }
                }
                then("view") {
                    then("player" to player()) {
                        executes {
                            val target = it.parseArgument<Player>("player")

                            it.sender.sendMessage("[System] 해당 플레이어의 금액은 ${target.wallet.getMoney().toMoneyFormat()} 입니다.")
                        }
                    }
                }
            }
        }
    }
}
