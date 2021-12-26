package com.tuwaiq.boredgames.SlidingPuzzle

class Tile(private var number: Int? = null) {
    fun number(): Int {
        return number!!
    }
}