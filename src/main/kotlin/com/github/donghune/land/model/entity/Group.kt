package com.github.donghune.land.model.entity

open class Group(
    val uuid: String,
    val name: String,
    val owner: String,
    val member: MutableList<String>,
    val vaultLevel: Int,
    val vaultGold: Int,
) {
    fun getType(): LandType {
        return when (this) {
            is Village -> {
                LandType.VILLAGE
            }
            is Nation -> {
                LandType.NATION
            }
            else -> {
                LandType.NONE
            }
        }
    }
}