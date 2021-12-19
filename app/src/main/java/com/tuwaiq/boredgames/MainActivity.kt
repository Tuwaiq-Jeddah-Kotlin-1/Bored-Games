package com.tuwaiq.boredgames

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.tuwaiq.boredgames.Auth.Login

class MainActivity : AppCompatActivity() {
    private lateinit var logo: ImageView

    // Animation starts during this activity then will transfer to dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logo = findViewById(R.id.ic_logo)
        // Setting up two animations with their respective time
        logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))
        Handler().postDelayed({
            logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                logo.visibility = View.GONE
                startActivity(Intent(this, Login::class.java))
                finish()
            },1000)
        },2000)

    }
}