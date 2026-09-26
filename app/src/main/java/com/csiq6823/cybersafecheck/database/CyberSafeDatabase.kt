package com.csiq6823.cybersafecheck.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [RiskAnswerEntity::class, AssessmentEntity::class],
    version = 1
)
@TypeConverters(CyberSafeTypeConverters::class)
abstract class CyberSafeDatabase : RoomDatabase() {
    abstract fun riskDao(): RiskDao
    abstract fun assessmentDao(): AssessmentDao
}
