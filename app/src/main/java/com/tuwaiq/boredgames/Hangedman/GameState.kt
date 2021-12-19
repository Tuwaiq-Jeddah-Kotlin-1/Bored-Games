package com.tuwaiq.boredgames.Hangedman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

sealed class GameState {
    class Running(val lettersUsed: String,
                  val underscoreWord: String,
                  val drawable: Int) : GameState()
    class Lost(val wordToGuess: String) : GameState()
    class Won(val wordToGuess: String) : GameState()
}