package com.tuwaiq.boredgames.Ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.boredgames.Auth.Login
import com.tuwaiq.boredgames.R
import com.tuwaiq.boredgames.Settings.BottomSheetFragment
import com.tuwaiq.boredgames.SlidingPuzzle.SettingsDialogFragment
import kotlinx.android.synthetic.main.activity_game1.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.sign_up_layout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

class HomePage : AppCompatActivity() {

    private lateinit var btnSound: Button
    private lateinit var btnPlay: Button
    private lateinit var btnSettings : Button
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var sharedPreferencesFour :SharedPreferences


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
        sharedPreferences = this.getSharedPreferences("Preference", Context.MODE_PRIVATE)
        sharedPreferencesFour = getSharedPreferences("profile", Context.MODE_PRIVATE)

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
    override fun onBackPressed() {
        Toast.makeText(applicationContext, getString(R.string.logout_first), Toast.LENGTH_SHORT).show()
    }
    var mediaPlayer = MediaPlayer()

    fun playSound(){

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if (!mediaPlayer.isPlaying){
            Snackbar.make(btnSound,getString(R.string.song_playing), Snackbar.LENGTH_SHORT).setAction("Action", null).show()
            //Toast.makeText(this,"The song is now playing", Toast.LENGTH_SHORT).show()
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.wii_music_background)
                mediaPlayer.start()
                mediaPlayer.setLooping(true)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }else{
            Snackbar.make(btnSound,getString(R.string.song_stopped), Snackbar.LENGTH_SHORT).setAction("Action", null).show()
            try {
                mediaPlayer.pause()
                mediaPlayer.stop()
                mediaPlayer.reset()
            }catch (e: IOException){
                e.printStackTrace()
            }

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.action_delete ->{
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.delete_account))
                    .setPositiveButton(getString(R.string.yes)){
                            dialog, _ ->
                        deleteAccount()
                        getLogOut()
                        dialog.dismiss()
                    }.setNegativeButton(getString(R.string.no)){
                            dialog, _ -> dialog.dismiss()
                    }.create().show()

            }


        }
        return super.onOptionsItemSelected(item)
    }




    private fun deleteAccount(){
        val uId =FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("Users").document("$uId").delete()


            .addOnSuccessListener {
            Toast.makeText(this,getString(R.string.deleted),Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
                Toast.makeText(this,"Error, Exception occurred", Toast.LENGTH_LONG
                ).show()
        }
    }
    private fun getLogOut(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferences.getString("EMAIL", "")
        sharedPreferences.getString("PASSWORD", "")
        editor.clear()
        editor.apply()
        val editorTwo: SharedPreferences.Editor = sharedPreferencesFour.edit()
        sharedPreferencesFour.getString("refUsername", "")
        editorTwo.clear()
        editorTwo.apply()
        startActivity(Intent(this, Login::class.java))
    }


}






