package com.csiq6823.cybersafecheck.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.csiq6823.cybersafecheck.RiskCategory
import java.util.UUID


@Entity
data class RiskAnswerEntity(
    @PrimaryKey val itemId: UUID,
    val question: String,
    val category: RiskCategory,
    val isFlagged: Boolean
)
