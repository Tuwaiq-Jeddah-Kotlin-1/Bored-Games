package com.tuwaiq.boredgames.Auth

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.boredgames.Data.Users
import com.tuwaiq.boredgames.R
import com.tuwaiq.boredgames.Ui.HomePage
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUp : AppCompatActivity() {

    private val memberRefCollection = Firebase.firestore.collection("Users")

    lateinit var btnSignUp: Button
    lateinit var usernameReg: TextInputEditText
    lateinit var etEmailReg: TextInputEditText
    lateinit var etPasswordReg: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val animationDrawable = sign_up_layout.background as AnimationDrawable
        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(2000)
            start()
        }

        supportActionBar?.hide()

        btnSignUp = findViewById(R.id.btn_signup)
        usernameReg = findViewById(R.id.et_username_reg)
        etEmailReg = findViewById(R.id.et_email_reg)
        etPasswordReg = findViewById(R.id.et_password_reg)

        btnSignUp.setOnClickListener {

            when {
                TextUtils.isEmpty(etEmailReg.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Email",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(etPasswordReg.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Password",
                        Toast.LENGTH_LONG
                    ).show()


                }
                else -> {
                    val email: String = etEmailReg.text.toString().trim { it <= ' ' }
                    val password: String = etPasswordReg.text.toString().trim { it <= ' ' }


                    // create an instance and create a register with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            // if the registration is successfully done
                            if (task.isSuccessful) {
                                //firebase register user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this,
                                    "You were registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //++++++++++++++++++++++++++

                                val usernameData = usernameReg.text.toString()
                                val emailData = etEmailReg.text.toString()
                                val member = Users(usernameData, emailData)
                                saveMember(member)

                                //++++++++++++++++++++++
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
    private fun saveMember(member: Users) = CoroutineScope(Dispatchers.IO).launch {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            memberRefCollection.document("$uid").set(member).addOnSuccessListener {
                Toast.makeText(this@SignUp, "Successfully saved data.", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this@SignUp, HomePage::class.java))

            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@SignUp, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}