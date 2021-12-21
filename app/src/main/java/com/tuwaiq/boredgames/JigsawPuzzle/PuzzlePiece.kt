package com.tuwaiq.boredgames.JigsawPuzzle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView

class PuzzlePiece(context: Context?) : AppCompatImageView(context!!) {
    var xCoord = 0
    var yCoord = 0
    var pieceWidth = 0
    var pieceHeight = 0
    var canMove = true
}