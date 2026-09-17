package com.csiq6823.cybersafecheck

import java.util.UUID

data class RiskItem(
    val id: UUID,
    val category: RiskCategory,
    val question: String,
    val explanation: String,
    var isFlagged: Boolean = false
)
