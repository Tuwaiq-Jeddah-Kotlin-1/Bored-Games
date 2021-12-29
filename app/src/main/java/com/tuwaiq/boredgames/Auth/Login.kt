package com.tuwaiq.boredgames.Auth


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.DocumentsContract
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.LayoutAnimationController
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.boredgames.R
import com.tuwaiq.boredgames.Settings.BottomSheetFragment
import com.tuwaiq.boredgames.Ui.HomePage
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    lateinit var etEmail : TextInputEditText
    lateinit var etPassword : TextInputEditText
    lateinit var btnLogin : Button
    lateinit var tvSignup : TextView
    lateinit var tvForgotPassword: TextView
    lateinit var checkRemember : CheckBox
    lateinit var sharedPreference : SharedPreferences
    lateinit var sharedPreferencesTwo : SharedPreferences
    var isChecked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val animationDrawable = layout_login.background as AnimationDrawable
        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(2000)
            start()
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvSignup = findViewById(R.id.tv_signup)
        tvForgotPassword = findViewById(R.id.forgot_password)
        checkRemember = findViewById(R.id.remember_me)


        sharedPreference = this.getSharedPreferences("Preference", Context.MODE_PRIVATE)
        isChecked = sharedPreference.getBoolean("CHECKBOX",false)

        supportActionBar?.hide()
        if (isChecked){

            startActivity(Intent(this, HomePage::class.java))

        }
        tvForgotPassword.setOnClickListener {
            val bottomSheetFragment = ForgotPasswordFragment()
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetForgot")

        }

        tvSignup.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
        btnLogin.setOnClickListener {

            when {
                TextUtils.isEmpty(etEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Email",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(etPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Password",
                        Toast.LENGTH_LONG
                    ).show()

                }
                else -> {
                    val email: String = etEmail.text.toString().trim { it <= ' ' }
                    val password: String = etPassword.text.toString().trim { it <= ' ' }


                    // create an instance and create a register with email and passwords
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            // if the registration is successfully done
                            if (task.isSuccessful) {
                                //firebase register user
                                //val firebaseUser: FirebaseUser = task.result!!.user!!

                                //==========


                                val checked: Boolean = checkRemember.isChecked
                                val editor: SharedPreferences.Editor = sharedPreference.edit()
                                editor.putString("EMAIL", etEmail.text.toString())
                                editor.putString("PASSWORD", etPassword.text.toString())
                                editor.putBoolean("CHECKBOX", checked)
                                editor.apply()
                                getUser()
//                                Toast.makeText(this, "${etEmail.text} ${etPassword.text} ${checked}", Toast.LENGTH_LONG).show()
//                                Toast.makeText(this, "Saved Preference ${etEmail.text} ${etPassword.text}", Toast.LENGTH_LONG).show()
                                //==========
                                Toast.makeText(
                                    this,
                                    "Welcome",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //++++++++++++++++++++++++++

                                startActivity(Intent(this, HomePage::class.java))

                                //++++++++++++++++++++++++++
                            } else {
                                // if the registration is not successful then show error massage
                                Toast.makeText(
                                    this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                }


            }

        }

    }

    private fun getUser() = CoroutineScope(Dispatchers.IO).launch{
        val uId =FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("Users").document("$uId")
            .get().addOnCompleteListener {
                if (it.result?.exists()!!) {
                    val name = it.result!!.getString("username")
                    sharedPreferencesTwo = getSharedPreferences("profile", Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor = sharedPreferencesTwo.edit()
                    editor.putString("refUsername",name.toString())
                    editor.apply()
                }else {
                    Log.e("error \n", "Nope")
                }
            }

    }

}