package com.tuwaiq.boredgames

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.tuwaiq.boredgames.Auth.Login
import com.tuwaiq.boredgames.Notifications.GamesNotificationRepo
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var logo: ImageView
    private lateinit var tvMotto: TextView

    // Animation starts during this activity then will transfer to dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        GamesNotificationRepo().myNotification(this)
        val sharedPreferencesThree = this.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferencesThree.getString("Settings", "")
        if (language == "ar"){
            localization(language)
        }


        logo = findViewById(R.id.anim_bored)
        // Setting up two animations with their respective time
        logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))
        Handler().postDelayed({
            logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                logo.visibility = View.GONE
                startActivity(Intent(this, Login::class.java))
                finish()
            },500)
        },3000)

        tvMotto = findViewById(R.id.motto)
        // Setting up two animations with their respective time
        tvMotto.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))
        Handler().postDelayed({
            tvMotto.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                tvMotto.visibility = View.GONE
                startActivity(Intent(this, Login::class.java))
                finish()
            },500)
        },3000)

    }
    private fun localization(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        //---------------------------------------------------------------
        this.resources?.updateConfiguration(config, this.resources.displayMetrics)
    }
}