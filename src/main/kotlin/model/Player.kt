package model

import javafx.scene.paint.Color

enum class Player {
    NONE,
    ONE,
    TWO;

    override fun toString(): String {
        return when(this){
            NONE -> "_"
            else -> this.ordinal.toString()
        }
    }

    fun getColor() : Color {
        return when(this) {
            ONE -> Color.RED
            TWO -> Color.YELLOW
            NONE -> Color.WHITE
        }
    }
}