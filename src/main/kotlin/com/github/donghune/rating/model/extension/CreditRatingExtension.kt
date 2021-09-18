package com.github.donghune.rating.model.extension

import com.github.donghune.rating.model.entity.CreditRating
import com.github.donghune.rating.model.entity.PlayerCreditRating
import com.github.donghune.rating.model.repository.CreditRatingConfigRepository
import com.github.donghune.rating.model.repository.PlayerCreditRatingRepository
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

fun PlayerCreditRating.getCreditRating(): CreditRating {
    return CreditRatingConfigRepository.get().creditRatingList
        .find { it.id == ratingId } ?: throw Exception("신용등급을 찾을 수 없습니다.")
}

fun PlayerCreditRating.save() {
    PlayerCreditRatingRepository.save(uuid.toString())
}

fun Player.getCreditRating(): PlayerCreditRating {
    return PlayerCreditRatingRepository.getSafety(uniqueId.toString())
}

fun OfflinePlayer.getCreditRating(): PlayerCreditRating {
    return PlayerCreditRatingRepository.getSafety(uniqueId.toString())
}
