package model

import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableObjectValue
import java.lang.Exception

object Model {

    class SimpleObjectValue<T>(initialValue: T) : ObservableObjectValue<T> {
        private var value = initialValue
        private val invalidationListeners = mutableListOf<InvalidationListener?>()
        private val changeListeners = mutableListOf<ChangeListener<in T>?>()
        override fun addListener(listener: InvalidationListener?) { invalidationListeners.add(listener) }
        override fun addListener(listener: ChangeListener<in T>?) { changeListeners.add(listener) }
        override fun removeListener(listener: InvalidationListener?) { invalidationListeners.remove(listener) }
        override fun removeListener(listener: ChangeListener<in T>?) { changeListeners.remove(listener) }
        override fun getValue(): T { return value }
        override fun get(): T { return value }
        fun set(value: T) {
            invalidationListeners.forEach { it?.invalidated(this) }
            changeListeners.forEach { it?.changed(this, this.value, value) }
            this.value = value
        }
    }

    var width = 8

    var height = 7

    const val length = 4

    var grid = Grid(width, height, length)

    val onGameWin = SimpleObjectProperty(Player.NONE)

    val onGameDraw = SimpleBooleanProperty(false)

    val onNextPlayer = SimpleObjectProperty(Player.NONE)

    val onPieceDropped = SimpleObjectValue<Piece?>(null)

    var onGameStart = SimpleBooleanProperty(false)

    fun startGame() {
        onGameStart.value = true
        onGameWin.value = Player.NONE
        onGameDraw.value = false
        grid = Grid(width, height, length)
        onNextPlayer.value = Player.ONE
    }

    fun dropPiece(column: Int) {
        if (onGameWin.value == Player.NONE && onGameDraw.value == false) {       // if game has not resolved yet ...
            onPieceDropped.set(grid.dropPiece(column, onNextPlayer.value))       // ... attempt to drop piece and notify listeners
            if (onPieceDropped.value != null) {                                  // if drop successful ...
                onGameWin.value = grid.hasWon()                                  // ... check resolution (win)
                onGameDraw.value = grid.hasDraw()                                // ... check resolution (draw)
                if (onGameWin.value == Player.NONE && onGameDraw.value == false) {
                    onNextPlayer.value = when(onNextPlayer.value) {              // ... set up next player
                        Player.ONE -> Player.TWO
                        Player.TWO -> Player.ONE
                        else -> throw Exception("Invalid game state in dropPiece: current player was Player.NONE")
                    }
                } else {
                    onGameStart.value = false
                }
            }
        }
    }

    fun getGround(col: Int) : Int {
        return grid.getGround(col)
    }
}