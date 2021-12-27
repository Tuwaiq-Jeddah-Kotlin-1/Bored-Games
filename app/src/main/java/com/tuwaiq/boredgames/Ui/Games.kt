package com.tuwaiq.boredgames.Ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.tuwaiq.boredgames.Hangedman.GameActivity1
import com.tuwaiq.boredgames.JigsawPuzzle.GameActivity2
import com.tuwaiq.boredgames.R
import com.tuwaiq.boredgames.SlidingPuzzle.GameActivity3

class Games : AppCompatActivity() {

    lateinit var cardViewGame1 : CardView
    lateinit var cardViewGame2 : CardView
    lateinit var cardViewGame3 : CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        cardViewGame1 = findViewById(R.id.cardView1)
        cardViewGame2 = findViewById(R.id.cardView2)
        cardViewGame3 = findViewById(R.id.cardView3)

        cardViewGame1.setOnClickListener {

            startActivity(Intent(this, GameActivity1::class.java))
        }

        cardViewGame2.setOnClickListener {

            startActivity(Intent(this, GameActivity2::class.java))
        }
        cardViewGame3.setOnClickListener {

            startActivity(Intent(this,GameActivity3::class.java))
        }
    }
}