package com.tuwaiq.boredgames.Settings

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
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
import java.util.*


class BottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var tvUsername : TextView
    private lateinit var etUsernameChange : EditText
    private lateinit var radioArb : RadioButton
    private lateinit var radioEng : RadioButton
    private lateinit var btnConfirm : Button
    private lateinit var btnLogout : Button
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var sharedPreferencesTwo : SharedPreferences
    private lateinit var sharedPreferencesThree :SharedPreferences

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
            AlertDialog.Builder(this.requireContext())
                .setTitle("Logging Out!!")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes"){
                        dialog, _ -> getLogOut()
                    startActivity(Intent(this.context, Login::class.java))
                    dialog.dismiss()
                }.setNegativeButton("No"){
                        dialog, _ -> dialog.dismiss()
                }.create().show()

        }

        btnConfirm.setOnClickListener {

            AlertDialog.Builder(this.requireContext())
                .setTitle("Settings")
                .setMessage("Are you sure you want to change settings?")
                .setPositiveButton("Yes"){
                        dialog, _ ->
                    editUser()
                    getUser()
                    changeLanguage()
                    startActivity(Intent(this.context, HomePage::class.java))
                    dialog.dismiss()
                }.setNegativeButton("No"){
                        dialog, _ -> dialog.dismiss()
                }.create().show()

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

    private fun localization(lang: String){
        val locale =Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        //---------------------------------------------------------------
        context?.resources?.updateConfiguration(config, requireContext().resources.displayMetrics)

        sharedPreferencesThree = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferencesThree.edit()
        editor.putString("Settings", "${locale}")
        editor.apply()

        startActivity(Intent(this.context, HomePage::class.java))

    }

    private fun changeLanguage(){
        val view: View = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)
        val builder = BottomSheetDialog(requireView().context!!)
        builder.setTitle("Change Language")
        val btnChangeLanguage = requireView()
        val listItems = arrayOf("Arabic", "English")

        val radioGroup = requireView()
        //radioGroup.setOnCheckedChangeListener { group, checkedId ->
            //val selectedLanguage:RadioButton=view.findViewById(checkedId)
            btnChangeLanguage.setOnClickListener {
                //Log.e("language","${selectedLanguage.text}")

                //if (selectedLanguage.text.toString()=="Arabic"){
                    localization("ar")

                }//else if (selectedLanguage.text.toString()=="English"){
                    localization("en")

                }
            }
        //}
        //builder.setContentView(view)
    //}

//}