package com.github.donghune.land.model.entity

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("TaxConfig")
data class TaxConfig(
    val personalToVillageTax: Double,
    val villageToNationTax: Double,
    val personalTaxTable: Map<Int, Int>,
    val groupTaxTable: Map<Int, Int>,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "personalToVillageTax" to personalToVillageTax.toString(),
            "villageToNationTax" to villageToNationTax.toString(),
            "personalTaxTable" to personalTaxTable,
            "groupTaxTable" to groupTaxTable,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): TaxConfig {
            return TaxConfig(
                (data["personalToVillageTax"] as String).toDouble(),
                (data["villageToNationTax"] as String).toDouble(),
                data["personalTaxTable"] as Map<Int, Int>,
                data["groupTaxTable"] as Map<Int, Int>,
            )
        }
    }
}
