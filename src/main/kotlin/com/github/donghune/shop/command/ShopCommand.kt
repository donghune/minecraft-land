package com.github.donghune.shop.command

import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.plugin
import com.github.donghune.shop.inventory.ShopInventory
import com.github.donghune.shop.model.ShopMessage
import com.github.donghune.shop.model.entity.Shop
import com.github.donghune.shop.model.repo.ShopRepository
import com.github.donghune.util.sendErrorMessage
import com.github.donghune.util.sendInfoMessage
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.string
import com.github.monun.kommand.kommand
import org.bukkit.Material
import org.bukkit.entity.Player

object ShopCommand {
    fun initialize() {
        plugin.kommand {
            register("shop") {
                require { it.isOp }
                then("open") {
                    then("shop" to ShopArgument) {
                        executes {
                            val player = it.sender as Player
                            val shop = it.parseArgument<Shop>("shop")

                            ShopInventory(shop).open(player)
                        }
                    }
                }
                then("create") {
                    then("shopName" to string()) {
                        executes {
                            val shopName = it.parseArgument<String>("shopName")

                            if (ShopRepository.get(shopName) != null) {
                                it.sender.sendErrorMessage("이미 존재하는 상점 입니다.")
                                return@executes
                            }

                            ShopRepository.create(shopName, Shop(shopName, mutableListOf(), shopName))
                            it.sender.sendInfoMessage("상점을 생성하였습니다.")
                            return@executes
                        }
                    }
                }
                then("remove") {
                    then("shopName" to string()) {
                        executes {
                            val shopName = it.parseArgument<String>("shopName")

                            if (ShopRepository.get(shopName) != null) {
                                it.sender.sendErrorMessage("존재하지 않는 상점 입니다.")
                                return@executes
                            }

                            ShopRepository.remove(shopName)
                            it.sender.sendInfoMessage("상점을 삭제하였습니다.")
                            return@executes
                        }
                    }
                }
                then("npc") {
                    then("shop" to ShopArgument) {
                        then("npcName" to string()) {
                            executes {
                                val shop = it.parseArgument<Shop>("shop")
                                val npcName = it.parseArgument<String>("npcName")

                                shop.npcName = npcName
                                it.sender.sendInfoMessage("해당 상점의 NPC 이름을 변경하였습니다.")
                            }
                        }
                    }
                }
                then("stuff") {
                    then("shop" to ShopArgument) {
                        then("add") {
                            then("buyPrice" to integer()) {
                                then("sellPrice" to integer()) {
                                    executes {
                                        val player = it.sender as Player
                                        val shop = it.parseArgument<Shop>("shop")
                                        val buyPrice = it.parseArgument<Int>("buyPrice")
                                        val sellPrice = it.parseArgument<Int>("sellPrice")
                                        val itemInMainHand = player.inventory.itemInMainHand

                                        if (itemInMainHand.type == Material.AIR) {
                                            player.sendErrorMessage("손에 아이템이 존재하지 않습니다.")
                                            return@executes
                                        }

                                        shop.addStuff(itemInMainHand, buyPrice, sellPrice)
                                        ShopRepository.update(shop.name, shop)
                                        player.sendInfoMessage(ShopMessage.ADD_SHOP_STUFF)
                                    }
                                }
                            }
                        }
                        then("remove") {
                            then("index" to integer()) {
                                executes {
                                    val player = it.sender as Player
                                    val shop = it.parseArgument<Shop>("shop")
                                    val index = it.parseArgument<Int>("index")

                                    shop.stuffList.removeAt(index)
                                    ShopRepository.update(shop.name, shop)
                                    player.sendInfoMessage(ShopMessage.REMOVE_SHOP_STUFF)
                                }
                            }
                        }
                    }
                }
                then("reload") {
                    executes {
                        ShopRepository.reload()
                        it.sender.sendInfoMessage("상점 플러그인을 리로드 하였습니다.")
                    }
                }
            }
        }
    }
}
