package com.csiq6823.cybersafecheck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.csiq6823.cybersafecheck.database.AssessmentEntity
import com.csiq6823.cybersafecheck.databinding.ListItemAssessmentBinding
import java.text.DateFormat

class AssessmentListAdapter {

    class AssessmentHolder(
        private val binding: ListItemAssessmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat =
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)

        fun bind(assessment: AssessmentEntity) {
            binding.assessmentDate.text = dateFormat.format(assessment.timestamp)
            binding.assessmentScore.text =
                "${assessment.flaggedCount} / ${assessment.totalCount} flagged"
        }
    }

    class AssessmentAdapter(
        private val assessments: List<AssessmentEntity>
    ) : RecyclerView.Adapter<AssessmentHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemAssessmentBinding.inflate(inflater, parent, false)
            return AssessmentHolder(binding)
        }

        override fun onBindViewHolder(holder: AssessmentHolder, position: Int) {
            holder.bind(assessments[position])
        }

        override fun getItemCount() = assessments.size
    }
}
