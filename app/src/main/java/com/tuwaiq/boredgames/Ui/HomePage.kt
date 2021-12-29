package com.tuwaiq.boredgames.Ui

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tuwaiq.boredgames.R
import com.tuwaiq.boredgames.Settings.BottomSheetFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.sign_up_layout
import java.io.IOException

class HomePage : AppCompatActivity() {

    private lateinit var btnSound: Button
    private lateinit var btnPlay: Button
    lateinit var btnSettings : Button


    //private val lang = resources.getStringArray(R.array.Languages)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val animationDrawable = home_layout.background as AnimationDrawable
        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(2000)
            start()
        }

        btnSound = findViewById(R.id.btn_sound)
        btnPlay = findViewById(R.id.btn_play_games)
        btnSettings = findViewById(R.id.btn_settings)


        btnPlay.setOnClickListener {
            startActivity(Intent(this, Games::class.java))
        }
        btnSound.setOnClickListener {
            playSound()
        }
        btnSettings.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")

        }
    }
    var mediaPlayer = MediaPlayer()



    fun playSound(){

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if (!mediaPlayer.isPlaying){
            Snackbar.make(btnSound,"The song is now playing", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
            //Toast.makeText(this,"The song is now playing", Toast.LENGTH_SHORT).show()
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.wii_music_background)
                mediaPlayer.start()
                mediaPlayer.setLooping(true)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }else{
            Toast.makeText(this,"The song Stopped", Toast.LENGTH_SHORT).show()
            try {
                mediaPlayer.pause()
                mediaPlayer.stop()
                mediaPlayer.reset()
            }catch (e: IOException){
                e.printStackTrace()
            }

        }
    }





}






