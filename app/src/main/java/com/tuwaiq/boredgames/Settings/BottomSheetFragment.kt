package com.tuwaiq.boredgames.Settings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tuwaiq.boredgames.R


class BottomSheetFragment : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {


    lateinit var etUsernameChange : EditText
    lateinit var radioArb : RadioButton
    lateinit var radioEng : RadioButton
    lateinit var btnConfirm : Button
    lateinit var btnLogout : Button
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
        etUsernameChange = view.findViewById(R.id.username_settings_et)
        radioArb = view.findViewById(R.id.arb_lang)
        radioEng = view.findViewById(R.id.eng_lang)
        btnConfirm = view.findViewById(R.id.btn_confirm)
        btnLogout = view.findViewById(R.id.btn_logout)



    }
    @SuppressLint("InflateParams")
    private fun bottomSheetProperties() {

        val view: View = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)
        val builder = BottomSheetDialog(view.context)
        builder.setTitle("Settings")
        builder.show()

    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

        spanner!!.text = this.resources.getStringArray(R.array.Languages)[position]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spanner!!.text = this.resources.getStringArray(R.array.Languages)[p2]
    }


}