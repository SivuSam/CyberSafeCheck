package com.csiq6823.cybersafecheck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.csiq6823.cybersafecheck.database.RiskAnswerEntity
import com.csiq6823.cybersafecheck.databinding.ListItemRiskBinding

class RiskListAdapter {

    class RiskHolder(
        private val binding: ListItemRiskBinding,
        private val onRowTapped: (RiskAnswerEntity) -> Unit,
        private val onSwitchToggled: (RiskAnswerEntity, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(riskAnswer: RiskAnswerEntity) {
            binding.riskCategory.text = riskAnswer.category.name.replace("_", " ")
            binding.riskQuestion.text = riskAnswer.question

            // Clear the listener before setting isChecked so restoring state
            // on a recycled holder doesn't fire a spurious toggle callback.
            binding.riskSwitch.setOnCheckedChangeListener(null)
            binding.riskSwitch.isChecked = riskAnswer.isFlagged

            // Tapping the question text (not the switch) opens the detail screen.
            val openDetail = android.view.View.OnClickListener { onRowTapped(riskAnswer) }
            binding.riskCategory.setOnClickListener(openDetail)
            binding.riskQuestion.setOnClickListener(openDetail)

            binding.riskSwitch.setOnCheckedChangeListener { _, isChecked ->
                onSwitchToggled(riskAnswer, isChecked)
            }
        }
    }

    class RiskAdapter(
        private val riskAnswers: List<RiskAnswerEntity>,
        private val onRowTapped: (RiskAnswerEntity) -> Unit,
        private val onSwitchToggled: (RiskAnswerEntity, Boolean) -> Unit
    ) : RecyclerView.Adapter<RiskHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiskHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemRiskBinding.inflate(inflater, parent, false)
            return RiskHolder(binding, onRowTapped, onSwitchToggled)
        }

        override fun onBindViewHolder(holder: RiskHolder, position: Int) {
            holder.bind(riskAnswers[position])
        }

        override fun getItemCount() = riskAnswers.size
    }
}
