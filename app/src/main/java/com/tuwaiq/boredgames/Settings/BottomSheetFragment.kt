package com.tuwaiq.boredgames.Settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.boredgames.Auth.Login
import com.tuwaiq.boredgames.R
import com.tuwaiq.boredgames.Ui.HomePage


class BottomSheetFragment : BottomSheetDialogFragment() {


    lateinit var etUsernameChange : EditText
    lateinit var radioArb : RadioButton
    lateinit var radioEng : RadioButton
    lateinit var btnConfirm : Button
    lateinit var btnLogout : Button

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

        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this.context, Login::class.java))
        }

        btnConfirm.setOnClickListener {
            updateSettings()
        }


    }

    private fun updateSettings(){
        val view: View = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)
        val builder = BottomSheetDialog(view.context)
        builder.setTitle("Settings")
        builder.show()
    }




}