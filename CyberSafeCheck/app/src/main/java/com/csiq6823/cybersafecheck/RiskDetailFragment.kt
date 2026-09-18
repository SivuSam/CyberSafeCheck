package com.csiq6823.cybersafecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.csiq6823.cybersafecheck.databinding.FragmentRiskDetailBinding
import java.util.UUID

private const val ARG_RISK_ITEM_ID = "risk_item_id"
class RiskDetailFragment : Fragment() {

    private var _binding: FragmentRiskDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val riskItemId: UUID by lazy {
        requireArguments().getSerializable(ARG_RISK_ITEM_ID) as UUID
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val riskItem = RiskLab.getRiskItem(riskItemId) ?: return

        binding.detailCategory.text = riskItem.category.name.replace("_", " ")
        binding.detailQuestion.text = riskItem.question
        binding.detailExplanation.text = riskItem.explanation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(riskItemId: UUID): RiskDetailFragment {
            return RiskDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_RISK_ITEM_ID, riskItemId)
                }
            }
        }
    }
}
