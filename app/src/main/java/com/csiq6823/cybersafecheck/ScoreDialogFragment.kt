package com.csiq6823.cybersafecheck

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs

class ScoreDialogFragment : DialogFragment() {

    private val args: ScoreDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Your Risk Score")
            .setMessage("Flagged: ${args.flaggedCount} / ${args.totalCount}\n\n${args.breakdown}")
            .setPositiveButton("Save to History") { _, _ ->
                setFragmentResult(
                    REQUEST_KEY_SAVE_SCORE,
                    bundleOf(
                        BUNDLE_KEY_FLAGGED_COUNT to args.flaggedCount,
                        BUNDLE_KEY_TOTAL_COUNT to args.totalCount
                    )
                )
            }
            .setNegativeButton("Close", null)
            .create()
    }

    companion object {
        const val REQUEST_KEY_SAVE_SCORE = "REQUEST_KEY_SAVE_SCORE"
        const val BUNDLE_KEY_FLAGGED_COUNT = "BUNDLE_KEY_FLAGGED_COUNT"
        const val BUNDLE_KEY_TOTAL_COUNT = "BUNDLE_KEY_TOTAL_COUNT"
    }
}
