package com.csiq6823.cybersafecheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csiq6823.cybersafecheck.database.AssessmentEntity
import com.csiq6823.cybersafecheck.database.RiskAnswerEntity
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class RiskListViewModel : ViewModel() {

    private val riskRepository = RiskRepository.get()

    /** Suspends until the answers are loaded (and seeded, if this is the first run). */
    suspend fun loadAnswers(): List<RiskAnswerEntity> = riskRepository.getAnswers()

    /**  the switch flips immediately in the UI, and this persists it. */
    fun setFlagged(itemId: UUID, flagged: Boolean) {
        viewModelScope.launch {
            riskRepository.setFlagged(itemId, flagged)
        }
    }

    /** clears every stored answer back to "not flagged". */
    suspend fun resetAnswers() {
        riskRepository.resetAllAnswers()
    }

    /**  records one "Calculate My Score" result to history. */
    suspend fun saveAssessment(flaggedCount: Int, totalCount: Int) {
        riskRepository.insertAssessment(
            AssessmentEntity(
                id = UUID.randomUUID(),
                timestamp = Date(),
                flaggedCount = flaggedCount,
                totalCount = totalCount
            )
        )
    }
}
