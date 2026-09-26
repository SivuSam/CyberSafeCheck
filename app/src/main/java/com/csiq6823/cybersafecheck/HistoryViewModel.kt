package com.csiq6823.cybersafecheck

import androidx.lifecycle.ViewModel
import com.csiq6823.cybersafecheck.database.AssessmentEntity

class HistoryViewModel : ViewModel() {

    private val riskRepository = RiskRepository.get()

    suspend fun loadAssessments(): List<AssessmentEntity> = riskRepository.getAssessments()
}
