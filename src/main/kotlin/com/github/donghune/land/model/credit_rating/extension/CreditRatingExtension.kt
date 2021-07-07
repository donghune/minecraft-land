package com.github.donghune.land.model.credit_rating.extension

import com.github.donghune.land.model.credit_rating.entity.CreditRating
import com.github.donghune.land.model.credit_rating.entity.PlayerCreditRating
import com.github.donghune.land.model.credit_rating.repository.CreditRatingConfigRepository
import com.github.donghune.land.model.credit_rating.repository.PlayerCreditRatingRepository
import org.bukkit.entity.Player

fun PlayerCreditRating.getCreditRating(): CreditRating {
    return CreditRatingConfigRepository.get().creditRatingList
        .find { it.id == ratingId } ?: throw Exception("신용등급을 찾을 수 없습니다.")
}

fun PlayerCreditRating.save() {
    com.github.donghune.land.model.credit_rating.repository.PlayerCreditRatingRepository.save(uuid.toString())
}

fun Player.getCreditRating(): PlayerCreditRating {
    return PlayerCreditRatingRepository.getSafety(uniqueId.toString())
}