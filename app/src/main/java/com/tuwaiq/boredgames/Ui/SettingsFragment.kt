package com.tuwaiq.boredgames.Ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tuwaiq.boredgames.R


class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private lateinit var btnSpinner : Spinner
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
        /*btnSpinner = view.findViewById(R.id.spinner1)

        bottomSheetProperties()
        val adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(view.context,
            R.array.Languages,
            android.R.layout.simple_list_item_1)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        btnSpinner.adapter = adapter
        btnSpinner.onItemSelectedListener = this*/
    }
    @SuppressLint("InflateParams")
    private fun bottomSheetProperties() {

        val view: View = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)
        val builder = BottomSheetDialog(view as Context)
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
