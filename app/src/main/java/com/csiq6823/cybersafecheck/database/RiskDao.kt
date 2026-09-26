package com.csiq6823.cybersafecheck.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.UUID

@Dao
interface RiskDao {

    @Query("SELECT * FROM RiskAnswerEntity")
    suspend fun getAll(): List<RiskAnswerEntity>

    @Query("SELECT COUNT(*) FROM RiskAnswerEntity")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(riskAnswers: List<RiskAnswerEntity>)

    @Query("UPDATE RiskAnswerEntity SET isFlagged = :isFlagged WHERE itemId = :itemId")
    suspend fun updateFlagged(itemId: UUID, isFlagged: Boolean)

    @Query("UPDATE RiskAnswerEntity SET isFlagged = 0")
    suspend fun resetAllFlags()
}
