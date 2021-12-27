package com.tuwaiq.boredgames.SlidingPuzzle

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tuwaiq.boredgames.R

class GameActivity3: AppCompatActivity() {

    private  var mainView: ViewGroup? = null
    private  var board: Board? = null
    private  var boardView: BoardView? = null
    private lateinit var moves: TextView
    private var boardSize = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game3)

        mainView = findViewById(R.id.mainView)
        moves = findViewById(R.id.moves)
        moves.setTextColor(Color.RED)
        moves.textSize = 22f
        newGame()

    }

    private fun newGame(){
        board = Board(boardSize)
        board!!.addBoardChangeListener(boardChangeListener)
        board!!.rearrange()
        mainView!!.removeView(boardView)
        boardView = BoardView(this, board!!)
        mainView!!.addView(boardView)
        moves.text = "Number of Movements: 0"
    }

    fun changeSize(newSize: Int){
        if (newSize != boardSize){
            boardSize = newSize
            newGame()
            boardView!!.invalidate()
        }
    }

    private val boardChangeListener: BoardChangeListener = object: BoardChangeListener{
        override fun tileSlid(from: Place?, to: Place?, numbOfMoves: Int) {
            moves.text = "Number of Movements: ${numbOfMoves}"
        }

        override fun solved(numbOfMoves: Int) {
            moves.text = "Solved in ${numbOfMoves} moves"

            AlertDialog.Builder(this@GameActivity3)
                .setTitle("Congratulations... !!")
                .setIcon(R.drawable.ic_celebration)
                .setMessage("You won in $numbOfMoves move... !! \nIf you want a New Game.. !!")
                .setPositiveButton("Yes"){
                    dialog, _ ->  board!!.rearrange()
                    moves.text = "Number of Movements: 0"
                    boardView!!.invalidate()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){
                    dialog, _ -> dialog.dismiss()
                }.create().show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_newgame ->{
                AlertDialog.Builder(this)
                    .setTitle("New Game")
                    .setIcon(R.drawable.ic_new_style_24)
                    .setMessage("Are you sure you want to begin a New Game?")
                    .setPositiveButton("Yes"){
                        dialog, _ -> board!!.rearrange()
                        moves.text = "Number of Movements: 0"
                        boardView!!.invalidate()
                        dialog.dismiss()
                    }.setNegativeButton("No"){
                        dialog, _ -> dialog.dismiss()
                    }.create().show()
                true
            }
            R.id.action_settings ->{
                val settings = SettingsDialogFragment(boardSize)
                settings.show(supportFragmentManager, "fragment_settings")
                true
            }
            R.id.action_help ->{
                AlertDialog.Builder(this)
                    .setTitle("Rules!!")
                    .setMessage("The goal of the puzzle is to place the tiles" +
                            " in order by making sliding moves that use the empty space." +
                            "The only valid moves are to move a tile which is immediately" +
                            "adjacent to the blank into the location of the blank space.")
                    .setPositiveButton("I Understand"){
                        dialog, _ -> dialog.dismiss()
                    }.create().show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}