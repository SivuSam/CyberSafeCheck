package com.csiq6823.cybersafecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
                val answers = riskListViewModel.loadAnswers()
                binding.riskRecyclerView.adapter = RiskListAdapter.RiskAdapter(
                    riskAnswers = answers,
                    onRowTapped = { riskAnswer -> showDetail(riskAnswer) },
                    onSwitchToggled = { riskAnswer, isChecked ->
                        riskListViewModel.setFlagged(riskAnswer.itemId, isChecked)
                    }
                )
            }
        }
    }

    private fun showDetail(riskAnswer: RiskAnswerEntity) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, RiskDetailFragment.newInstance(riskAnswer.itemId))
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
