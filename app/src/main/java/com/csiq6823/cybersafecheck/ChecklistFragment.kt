package com.csiq6823.cybersafecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.csiq6823.cybersafecheck.database.RiskAnswerEntity
import com.csiq6823.cybersafecheck.databinding.FragmentChecklistBinding
import kotlinx.coroutines.launch

class ChecklistFragment : Fragment() {

    private var _binding: FragmentChecklistBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val riskListViewModel: RiskListViewModel by viewModels()
    private var currentAnswers: List<RiskAnswerEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        binding.riskRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reload()
            }
        }

        binding.btnCalculateScore.setOnClickListener { showScoreDialog() }

        binding.btnResetChecklist.setOnClickListener {
            findNavController().navigate(ChecklistFragmentDirections.showResetConfirmation())
        }

        binding.btnViewHistory.setOnClickListener {
            findNavController().navigate(ChecklistFragmentDirections.showHistory())
        }

        setFragmentResultListener(
            ResetConfirmationDialogFragment.REQUEST_KEY_RESET_CONFIRMED
        ) { _, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                riskListViewModel.resetAnswers()
                reload()
            }
        }

        setFragmentResultListener(
            ScoreDialogFragment.REQUEST_KEY_SAVE_SCORE
        ) { _, bundle ->
            val flaggedCount = bundle.getInt(ScoreDialogFragment.BUNDLE_KEY_FLAGGED_COUNT)
            val totalCount = bundle.getInt(ScoreDialogFragment.BUNDLE_KEY_TOTAL_COUNT)
            viewLifecycleOwner.lifecycleScope.launch {
                riskListViewModel.saveAssessment(flaggedCount, totalCount)
                findNavController().navigate(ChecklistFragmentDirections.showHistory())
            }
        }
    }

    private suspend fun reload() {
        currentAnswers = riskListViewModel.loadAnswers()
        bindAdapter()
    }

    private fun bindAdapter() {
        binding.riskRecyclerView.adapter = RiskListAdapter.RiskAdapter(
            riskAnswers = currentAnswers,
            onRowTapped = { riskAnswer -> showDetail(riskAnswer) },
            onSwitchToggled = { riskAnswer, isChecked ->
                riskListViewModel.setFlagged(riskAnswer.itemId, isChecked)
                currentAnswers = currentAnswers.map {
                    if (it.itemId == riskAnswer.itemId) it.copy(isFlagged = isChecked) else it
                }
            }
        )
    }

    private fun showDetail(riskAnswer: RiskAnswerEntity) {
        findNavController().navigate(
            ChecklistFragmentDirections.showRiskDetail(riskAnswer.itemId)
        )
    }

    private fun showScoreDialog() {
        val flaggedCount = currentAnswers.count { it.isFlagged }
        val totalCount = currentAnswers.size
        val breakdown = currentAnswers
            .groupBy { it.category }
            .entries
            .joinToString(separator = "\n") { (category, itemsInCategory) ->
                val flaggedInCategory = itemsInCategory.count { it.isFlagged }
                "${category.name.replace("_", " ")}: $flaggedInCategory/${itemsInCategory.size} flagged"
            }

        findNavController().navigate(
            ChecklistFragmentDirections.showScoreDialog(flaggedCount, totalCount, breakdown)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
