package com.github.donghune.land.model.entity

enum class LandType(
    val korName: String
) {
    NONE("기본"),
    PERSONAL("개인"),
    VILLAGE("마을"),
    NATION("국가");

    fun getLandBuyPrice(): Long {
        return when (this) {
            PERSONAL -> 1792800
            VILLAGE -> 2988000
            NATION -> 5976000
            else -> return 0
        }
    }

}