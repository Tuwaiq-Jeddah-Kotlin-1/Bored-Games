package com.tuwaiq.boredgames.SlidingPuzzle

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tuwaiq.boredgames.R

class SettingsDialogFragment(var size: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.size_puzzle))
            .setSingleChoiceItems(R.array.size_options, size-2){
                dialog, which -> size = which + 2
            }
            .setPositiveButton(getString(R.string.change)){
                dialog, id ->
                (getActivity() as GameActivity3).changeSize(size)
            }
            .setNegativeButton(getString(R.string.cancel)){
                dialog, _ -> dialog.cancel()
            }
        val settingDialog = builder.create()
        settingDialog.show()

        return settingDialog
    }
}