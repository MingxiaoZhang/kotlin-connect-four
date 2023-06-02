package ui

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener.Change
import javafx.scene.Group
import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import model.Model
import model.Piece
import model.Player

const val gridSize = 70.0
class ConnectFourGrid
    : Group(), ChangeListener<Piece?> {
    init {
        println(Model.width)
        for (i in 0 until Model.width){
            for (j in 0 until Model.height){
                children.add(GridItem(i.toDouble() * gridSize,
                    j.toDouble() * gridSize, gridSize).item)
            }
        }
        Model.onPieceDropped.addListener(this)
    }
    override fun changed(
        observable: ObservableValue<out Piece?>?,
        oldValue: Piece?,
        newValue: Piece?
    ) {
        if (newValue != null) {
            val piece = Circle((newValue.x * gridSize + gridSize / 2).toDouble(),
                (newValue.y * gridSize + gridSize / 2).toDouble(),
                gridSize / 2,
                newValue.player.getColor())
            children.add(piece)
            piece.toBack()
        }
    }

}