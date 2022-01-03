package com.tuwaiq.boredgames.SlidingPuzzle

import android.annotation.SuppressLint
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
        moves.text = getString(R.string.number_of_movements)
    }

    fun changeSize(newSize: Int){
        if (newSize != boardSize){
            boardSize = newSize
            newGame()
            boardView!!.invalidate()
        }
    }

    private val boardChangeListener: BoardChangeListener = object: BoardChangeListener{
        @SuppressLint("SetTextI18n")
        override fun tileSlid(from: Place?, to: Place?, numbOfMoves: Int) {
            moves.text = "${getString(R.string.number_movements)} ${numbOfMoves}"
        }

        @SuppressLint("SetTextI18n")
        override fun solved(numbOfMoves: Int) {
            moves.text = "${getString(R.string.solved_in)} ${numbOfMoves} ${getString(R.string.moves)}"

            AlertDialog.Builder(this@GameActivity3)
                .setTitle(getString(R.string.congratulations))
                .setIcon(R.drawable.ic_celebration)
                .setMessage("${getString(R.string.you_won_in)} $numbOfMoves ${getString(R.string.move__)} \n ${getString(R.string.want_new_game)}")
                .setPositiveButton(getString(R.string.yes)){
                    dialog, _ ->  board!!.rearrange()
                    moves.text = getString(R.string.number_of_movements)
                    boardView!!.invalidate()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no)){
                    dialog, _ -> dialog.dismiss()
                }.create().show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId){
            R.id.action_newgame ->{
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.new_game))
                    .setIcon(R.drawable.ic_new_style_24)
                    .setMessage(getString(R.string.begin_new_game))
                    .setPositiveButton(getString(R.string.yes)){
                        dialog, _ -> board!!.rearrange()
                        moves.text = getString(R.string.number_of_movements)
                        boardView!!.invalidate()
                        dialog.dismiss()
                    }.setNegativeButton(getString(R.string.no)){
                        dialog, _ -> dialog.dismiss()
                    }.create().show()

            }
            R.id.action_settings ->{
                val settings = SettingsDialogFragment(boardSize)
                settings.show(supportFragmentManager, "fragment_settings")

            }
            R.id.action_help ->{
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.rules))
                    .setMessage(getString(R.string.dialog1))
                    .setPositiveButton(getString(R.string.i_understand)){
                        dialog, _ -> dialog.dismiss()
                    }.show()

            }


        }
        return super.onOptionsItemSelected(item)
    }
}