package com.tuwaiq.boredgames.SlidingPuzzle

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tuwaiq.boredgames.R

class GameActivity3: AppCompatActivity() {

    private lateinit var mainView: ViewGroup
    private lateinit var board: Board
    private lateinit var boardView: BoardView
    private lateinit var moves: TextView
    private var boardSize = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game3)
    }
}