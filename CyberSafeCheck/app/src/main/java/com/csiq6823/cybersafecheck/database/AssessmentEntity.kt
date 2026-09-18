package com.csiq6823.cybersafecheck.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
@Entity
data class AssessmentEntity(
    @PrimaryKey val id: UUID,
    val timestamp: Date,
    val flaggedCount: Int,
    val totalCount: Int
)
