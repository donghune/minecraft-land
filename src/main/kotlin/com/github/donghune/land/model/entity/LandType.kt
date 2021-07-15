package com.github.donghune.land.model.entity

enum class LandType {
    NONE,
    PERSONAL,
    VILLAGE,
    NATION;

    fun getLandBuyPrice(): Long {
        return when (this) {
            PERSONAL -> 1792800
            VILLAGE -> 2988000
            NATION -> 5976000
            else -> return 0
        }
    }

}