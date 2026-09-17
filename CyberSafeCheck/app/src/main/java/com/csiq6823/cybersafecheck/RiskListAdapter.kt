package com.csiq6823.cybersafecheck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.csiq6823.cybersafecheck.databinding.ListItemRiskBinding

class RiskListAdapter {

    class RiskHolder(
        private val binding: ListItemRiskBinding,
        private val onRowTapped: (RiskItem) -> Unit,
        private val onSwitchToggled: (RiskItem, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(riskItem: RiskItem) {
            binding.riskCategory.text = riskItem.category.name.replace("_", " ")
            binding.riskQuestion.text = riskItem.question

            binding.riskSwitch.setOnCheckedChangeListener(null)
            binding.riskSwitch.isChecked = riskItem.isFlagged


            binding.riskTextArea.setOnClickListener {
                onRowTapped(riskItem)
            }

            binding.riskSwitch.setOnCheckedChangeListener { _, isChecked ->
                onSwitchToggled(riskItem, isChecked)
            }
        }
    }

    class RiskAdapter(
        private val riskItems: List<RiskItem>,
        private val onRowTapped: (RiskItem) -> Unit,
        private val onSwitchToggled: (RiskItem, Boolean) -> Unit
    ) : RecyclerView.Adapter<RiskHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiskHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemRiskBinding.inflate(inflater, parent, false)
            return RiskHolder(binding, onRowTapped, onSwitchToggled)
        }

        override fun onBindViewHolder(holder: RiskHolder, position: Int) {
            holder.bind(riskItems[position])
        }

        override fun getItemCount() = riskItems.size
    }
}
