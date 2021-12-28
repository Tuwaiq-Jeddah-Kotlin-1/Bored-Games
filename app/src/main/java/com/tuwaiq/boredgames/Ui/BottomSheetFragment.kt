package com.tuwaiq.boredgames.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tuwaiq.boredgames.R


class BottomSheetFragment : BottomSheetDialogFragment() {


    lateinit var radioArb : RadioButton
    lateinit var radioEng : RadioButton
    private var spanner : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioArb = view.findViewById(R.id.arb_lang)
        radioEng = view.findViewById(R.id.eng_lang)



    }


}