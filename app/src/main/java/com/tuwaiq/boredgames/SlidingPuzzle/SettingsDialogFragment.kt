package com.tuwaiq.boredgames.SlidingPuzzle

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tuwaiq.boredgames.R

class SettingsDialogFragment(var size: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
            .setTitle("Define the size of the puzzle")
            .setSingleChoiceItems(R.array.size_options, size-2){
                dialog, which -> size = which + 2
            }
            .setPositiveButton("Change"){
                dialog, id ->
                (getActivity() as GameActivity3).changeSize(size)
            }
            .setNegativeButton("Cancel"){
                dialog, _ -> dialog.cancel()
            }
        val settingDialog = builder.create()
        settingDialog.show()

        return settingDialog
    }
}