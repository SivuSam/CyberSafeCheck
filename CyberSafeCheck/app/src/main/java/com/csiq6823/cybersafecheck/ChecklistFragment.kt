package com.csiq6823.cybersafecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.csiq6823.cybersafecheck.databinding.FragmentChecklistBinding


class ChecklistFragment : Fragment() {

    private var _binding: FragmentChecklistBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.riskRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.riskRecyclerView.adapter = RiskListAdapter.RiskAdapter(
            riskItems = RiskLab.getRiskItems(),
            onRowTapped = { riskItem -> showDetail(riskItem) },
            onSwitchToggled = { riskItem, isChecked ->
                RiskLab.updateFlagged(riskItem.id, isChecked)
            }
        )
    }

    private fun showDetail(riskItem: RiskItem) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, RiskDetailFragment.newInstance(riskItem.id))
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
