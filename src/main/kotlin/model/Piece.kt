package model

class Piece(val x: Int, val y: Int) {

    var player = Player.NONE
        set(value) { if (player == Player.NONE) field = value }

    override fun toString(): String {
        return player.toString()
        //return "($x,$y):${player.toString()}"
    }
}