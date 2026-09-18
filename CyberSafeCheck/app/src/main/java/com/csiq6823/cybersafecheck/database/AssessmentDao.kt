package com.csiq6823.cybersafecheck.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface AssessmentDao {

    @Insert
    suspend fun insert(assessment: AssessmentEntity)

    @Query("SELECT * FROM AssessmentEntity ORDER BY timestamp DESC")
    suspend fun getAll(): List<AssessmentEntity>
}
