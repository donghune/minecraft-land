package com.github.donghune.rating.command

import com.github.donghune.plugin
import com.github.donghune.rating.model.extension.getCreditRating
import com.github.donghune.rating.model.extension.save
import com.github.donghune.util.sendInfoMessage
import com.github.monun.kommand.argument.player
import com.github.monun.kommand.kommand
import org.bukkit.entity.Player

object CreditRatingCommand {
    fun initialize() {
        plugin.kommand {
            register("credit") {
                require { it.isOp }
                then("up") {
                    then("user" to player()) {
                        executes {
                            val target: Player = it.parseArgument("user")

                            target.getCreditRating().apply { ratingId++ }.save()
                            it.sender.sendInfoMessage("해당 플레이어의 신용등급을 상승시켰습니다.")
                        }
                    }
                }
                then("down") {
                    then("user" to player()) {
                        executes {
                            val target: Player = it.parseArgument("user")

                            target.getCreditRating().apply { ratingId-- }.save()
                            it.sender.sendInfoMessage("해당 플레이어의 신용등급을 하락시켰습니다.")
                        }
                    }
                }
            }
        }
    }
}

/*
신용등급
1등급(AAA+,AAA,AAA-) > 2등급(AA+,AA,AA-) > 3등급(A+,A,A-) > 4등급(BBB+,BBB,BBB-) > 5등급(BB+,BB,BB-) > 6등급(B+,B,B-) > 7등급(CCC+,CCC,CCC-) > 8등급(CC+,CC,CC-) > 9등급(C+,C,C-) > 10등급(DDD+,DDD,DDD-) > 11등급(DD+,DD,DD-) > 12등급(D+,D,D-) > 13등급(EEE,EE,E) > 14등급(FFF,FF,F)
상점에서 2,988,000쉼을 지불하고 등급 상승 (예시 : AA- >>> AA)
관리자가 명령어를 사용하여 등급을 조정할 수 있음
상승 명령어 - /credit up user
하양 명령어 - /credit down user
명령어 사용 예
예1 : /credit up clur__
예2 : /credit down clur__
각 등급마다 [B+]와 같은 채팅창에 표시되는 칭호를 서버 파일에서 설정할 수 있게 해야됨
예 :[ 토깽이 ] [B+] username
아래와 같이 등급이 표시됨
 */
