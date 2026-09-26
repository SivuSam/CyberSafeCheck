package com.csiq6823.cybersafecheck.database

import androidx.room.TypeConverter
import com.csiq6823.cybersafecheck.RiskCategory
import java.util.Date
import java.util.UUID

class CyberSafeTypeConverters {

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date = Date(millisSinceEpoch)

    @TypeConverter
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun toUUID(uuid: String): UUID = UUID.fromString(uuid)

    @TypeConverter
    fun fromRiskCategory(category: RiskCategory): String = category.name

    @TypeConverter
    fun toRiskCategory(category: String): RiskCategory = RiskCategory.valueOf(category)
}
