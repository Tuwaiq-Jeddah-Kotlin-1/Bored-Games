package com.tuwaiq.boredgames.SlidingPuzzle

interface BoardChangeListener {
    fun tileSlid(from: Place?, to: Place?, numbOfMoves: Int)

    fun solved(numbOfMoves: Int)
}