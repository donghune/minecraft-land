package com.github.donghune.warp

import com.github.donghune.namulibrary.struct.SubMainManager
import com.github.donghune.util.sendInfoMessage
import com.github.donghune.warp.listener.WarpListener
import com.github.donghune.warp.model.WarpConfig
import com.github.donghune.warp.model.WarpConfigRepository
import com.github.donghune.warp.model.WarpPoint
import com.github.monun.kommand.kommand
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class WarpPlugin(dataFolder: File, val plugin: JavaPlugin) : SubMainManager(dataFolder, plugin) {
    override fun onDisable() {
    }

    override fun setupCommands() {
        plugin.kommand {
            register("warpa") {
                require { it.isOp }
                executes {
                    WarpConfigRepository.reload()
                    it.sender.sendInfoMessage("워프 플러그인을 리로드 하셨습니다.")
                }
            }
        }
    }

    override fun setupListeners() {
        registerEvent(WarpListener())
    }

    override fun setupRegisterClass() {
        registerClass(WarpPoint::class.java)
        registerClass(WarpConfig::class.java)
    }

    override fun setupSchedulers() {
    }
}
