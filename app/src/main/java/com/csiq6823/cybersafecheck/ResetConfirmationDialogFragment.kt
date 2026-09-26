package com.csiq6823.cybersafecheck

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class ResetConfirmationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Reset Checklist")
            .setMessage(
                "This clears every answers recorded on this checklist. " +
                    "This can't be undone. Continue?"
            )
            .setPositiveButton("Reset") { _, _ ->
                setFragmentResult(REQUEST_KEY_RESET_CONFIRMED, Bundle())
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    companion object {
        const val REQUEST_KEY_RESET_CONFIRMED = "REQUEST_KEY_RESET_CONFIRMED"
    }
}
