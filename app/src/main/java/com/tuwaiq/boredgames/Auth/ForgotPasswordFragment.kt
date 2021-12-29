package com.tuwaiq.boredgames.Auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.boredgames.R


class ForgotPasswordFragment : BottomSheetDialogFragment() {

    lateinit var btnSend: Button
    lateinit var etEmailForgot: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSend = view.findViewById(R.id.btn_send)
        etEmailForgot = view.findViewById(R.id.forgot_email_et)

        btnSend.setOnClickListener {
            sendingEmail()
        }




    }

    private fun sendingEmail() {
        val email = etEmailForgot.text.toString().trim { it <= ' ' }

        if (email.isEmpty()) {
            Toast.makeText(context, "Please enter your E-mail", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context, "Email was successfully sent",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Toast.makeText(
                            context, "The email is incorrect",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

}