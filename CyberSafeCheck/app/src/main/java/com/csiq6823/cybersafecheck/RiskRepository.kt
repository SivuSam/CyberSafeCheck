package com.csiq6823.cybersafecheck

import android.content.Context
import androidx.room.Room
import com.csiq6823.cybersafecheck.database.AssessmentEntity
import com.csiq6823.cybersafecheck.database.CyberSafeDatabase
import com.csiq6823.cybersafecheck.database.RiskAnswerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

private const val DATABASE_NAME = "cybersafecheck-database"
class RiskRepository private constructor(context: Context) {

    private val database: CyberSafeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CyberSafeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val riskDao = database.riskDao()
    private val assessmentDao = database.assessmentDao()

    /** Returns every checklist answer, seeding the table from RiskLab the first time this runs. */
    suspend fun getAnswers(): List<RiskAnswerEntity> = withContext(Dispatchers.IO) {
        seedIfEmpty()
        riskDao.getAll()
    }

    suspend fun setFlagged(itemId: UUID, flagged: Boolean) = withContext(Dispatchers.IO) {
        riskDao.updateFlagged(itemId, flagged)
    }

    suspend fun insertAssessment(assessment: AssessmentEntity) = withContext(Dispatchers.IO) {
        assessmentDao.insert(assessment)
    }

    suspend fun getAssessments(): List<AssessmentEntity> = withContext(Dispatchers.IO) {
        assessmentDao.getAll()
    }

    private suspend fun seedIfEmpty() {
        if (riskDao.count() == 0) {
            val seedRows = RiskLab.getRiskItems().map { item ->
                RiskAnswerEntity(
                    itemId = item.id,
                    question = item.question,
                    category = item.category,
                    isFlagged = item.isFlagged
                )
            }
            riskDao.insertAll(seedRows)
        }
    }

    companion object {
        private var INSTANCE: RiskRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RiskRepository(context)
            }
        }

        fun get(): RiskRepository {
            return INSTANCE
                ?: throw IllegalStateException("RiskRepository must be initialized")
        }
    }
}
