package com.github.donghune.land.schduler

import com.github.donghune.land.extension.paymentTax
import com.github.donghune.land.extension.paymentToNation
import com.github.donghune.land.extension.paymentToVillage
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.PlayTimeConfigRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.namulibrary.schedular.SchedulerManager
import org.bukkit.Bukkit

val PersonalTaxScheduler = SchedulerManager {

    val ptRepo = PlayTimeConfigRepository.get()

    started {
    }

    doing {
        ptRepo.server++
        if (ptRepo.server % 3600 != 0) {
            return@doing
        }

        // 1. 개인 -> 마을 금고
        Bukkit.getOnlinePlayers().forEach { it.paymentToVillage() }

        // 2. 마을 금고  -> 국가 금고
        VillageRepository.getList().forEach { it.paymentToNation() }

        // 3. 개인 -> 서버
        Bukkit.getOnlinePlayers().forEach { it.paymentTax() }

        // 4. 마을 -> 서버
        VillageRepository.getList().forEach { it.paymentTax() }

        // 5. 국가 -> 서버
        NationRepository.getList().forEach { it.paymentTax() }
    }

    finished {
    }
}
