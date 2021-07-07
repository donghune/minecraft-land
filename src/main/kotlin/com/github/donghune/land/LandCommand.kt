package com.github.donghune.land

import com.github.monun.kommand.kommand

object LandCommand {
    fun initialize() {
        plugin.kommand {
            register("land") {
                // open my land inventory
            }
        }
    }
}
