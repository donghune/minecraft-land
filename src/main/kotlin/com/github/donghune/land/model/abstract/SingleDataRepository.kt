package com.github.donghune.land.model.abstract

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.io.File

abstract class SingleDataRepository<T : ConfigurationSerializable> {
    abstract val file: File
    abstract val dataType: Class<T>

    private var config: T? = null
    private lateinit var configFile: File
    private lateinit var yamlConfiguration: YamlConfiguration

    abstract fun getDefaultData(): T

    fun get(): T {
        // config 파일 생성 체크
        if (!this::configFile.isInitialized) {
            configFile = file
        }

        // yaml 초기화 체크
        if (!this::yamlConfiguration.isInitialized) {
            yamlConfiguration = YamlConfiguration.loadConfiguration(configFile)
        }

        // 데이터 여부
        if (!yamlConfiguration.getKeys(false).contains("data")) {
            config = getDefaultData()
            yamlConfiguration.set("data", config)
            yamlConfiguration.save(configFile)
        }

        config = yamlConfiguration.get("data") as T

        return config!!
    }

    fun save() {
        yamlConfiguration.set("data", config)
        yamlConfiguration.save(configFile)
    }

    fun reload() {
        config = yamlConfiguration.get("data") as T
    }
}
