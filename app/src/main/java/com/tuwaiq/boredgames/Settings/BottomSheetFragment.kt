package com.tuwaiq.boredgames.Settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.boredgames.Auth.Login
import com.tuwaiq.boredgames.Auth.SignUp
import com.tuwaiq.boredgames.R
import com.tuwaiq.boredgames.Ui.HomePage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BottomSheetFragment : BottomSheetDialogFragment() {


    lateinit var tvUsername : TextView
    lateinit var etUsernameChange : EditText
    lateinit var radioArb : RadioButton
    lateinit var radioEng : RadioButton
    lateinit var btnConfirm : Button
    lateinit var btnLogout : Button
    lateinit var sharedPreferences : SharedPreferences
    lateinit var sharedPreferencesTwo : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)
        sharedPreferencesTwo = this.requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE)
        etUsernameChange = view.findViewById(R.id.username_settings_et)
        radioArb = view.findViewById(R.id.arb_lang)
        radioEng = view.findViewById(R.id.eng_lang)
        btnConfirm = view.findViewById(R.id.btn_confirm)
        btnLogout = view.findViewById(R.id.btn_logout)
        tvUsername = view.findViewById(R.id.username_settings)
        val userReference = sharedPreferencesTwo.getString("refUsername"," ")
        tvUsername.text = userReference

        getUser()

        btnLogout.setOnClickListener {
            //FirebaseAuth.getInstance().signOut()
            getLogOut()
        }

        btnConfirm.setOnClickListener {

            editUser()
            getUser()
            startActivity(Intent(this.context, HomePage::class.java))
        }


    }

    private fun getLogOut(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferences.getString("EMAIL", "")
        sharedPreferences.getString("PASSWORD", "")
        editor.clear()
        editor.apply()
        val editorTwo: SharedPreferences.Editor = sharedPreferencesTwo.edit()
        sharedPreferencesTwo.getString("refUsername", "")
        editorTwo.clear()
        editorTwo.apply()
        startActivity(Intent(this.context, Login::class.java))
    }

    private fun getUser() = CoroutineScope(Dispatchers.IO).launch{
        val uId =FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("Users").document("$uId")
            .get().addOnCompleteListener {
                if (it.result?.exists()!!) {
                    val name = it.result!!.getString("username")
                    sharedPreferencesTwo = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor = sharedPreferencesTwo.edit()
                    editor.putString("refUsername",name.toString())
                    editor.apply()
                }else {
                    Log.e("error \n", "Nope")
                }
            }

    }
    private fun editUser(){
        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val upDateUserData = Firebase.firestore.collection("Users")
        upDateUserData.document(uId.toString()).update("username", etUsernameChange.text.toString())
        Toast.makeText(context,"edit is successful",Toast.LENGTH_LONG).show()

    }




}