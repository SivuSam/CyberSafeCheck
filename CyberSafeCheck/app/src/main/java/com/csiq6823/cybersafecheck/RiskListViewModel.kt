package com.csiq6823.cybersafecheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csiq6823.cybersafecheck.database.RiskAnswerEntity
import kotlinx.coroutines.launch
import java.util.UUID

class RiskListViewModel : ViewModel() {

    private val riskRepository = RiskRepository.get()

    /** Suspends until the answers are loaded (and seeded, if this is the first run). */
    suspend fun loadAnswers(): List<RiskAnswerEntity> = riskRepository.getAnswers()

    /** Fire-and-forget write: the switch flips immediately in the UI, and this persists it. */
    fun setFlagged(itemId: UUID, flagged: Boolean) {
        viewModelScope.launch {
            riskRepository.setFlagged(itemId, flagged)
        }
    }
}
