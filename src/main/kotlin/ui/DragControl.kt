package ui

import javafx.animation.AnimationTimer
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import model.Model
import model.Player
import kotlin.math.abs
import kotlin.math.floor

data class DragInfo(var target : Circle? = null,
                    var anchorX: Double = 0.0,
                    var anchorY: Double = 0.0,
                    var initialX: Double = 0.0,
                    var initialY: Double = 0.0)

class DragControl(x: Double, y: Double, col: Color, private val player: Player) : Circle(x, y, gridSize / 2 - 5, col),
    ChangeListener<Player> {
    private var dragInfo = DragInfo()
    private val player2X = Model.width * gridSize + 130.0
    init {
        isVisible = (player == Player.ONE)
        Model.onNextPlayer.addListener(this)
        addEventFilter(MouseEvent.MOUSE_PRESSED) {
            dragInfo = DragInfo(this, it.sceneX, it.sceneY, translateX, translateY)
        }
        addEventFilter(MouseEvent.MOUSE_DRAGGED) {
            if (it.sceneX < Model.width * gridSize + 240.0 && it.sceneX > 0.0) {
                translateX = getDragValue(dragInfo.initialX + it.sceneX - dragInfo.anchorX)
            }
            val temp = dragInfo.initialY + it.sceneY - dragInfo.anchorY
            if (temp < 10 && temp > -10) {
                translateY = temp
            }
        }
        addEventFilter(MouseEvent.MOUSE_RELEASED) {
            val column = floor((translateX - 65.0) / gridSize).toInt()
            if (isDroppable(column)) {
                animateDown(column, 3)
            } else {
                animateBack()
            }
            dragInfo = DragInfo()
        }
    }
    private fun getDragValue(x : Double) : Double{
        return if (x < Model.width * gridSize + 65 && x > 65) {
            ((x - 65) / gridSize).toInt() * gridSize + 100.0
        } else {
            x
        }
    }

    private fun animateDown(col : Int, bounce : Int) {
        val ground = Model.getGround(col) * gridSize
        translateX = col * gridSize + 100
        var velocity = 0
        val animation = object : AnimationTimer() {
            override fun handle(now: Long) {
                translateY += velocity
                velocity += 1
                if (abs(translateY) > abs(ground)) {
                    stop()
                    if (bounce == 0) {
                        Model.dropPiece(col)
                    } else {
                        animateUp(0.5 * velocity, col, bounce)
                    }
                }
            }
        }
        animation.start()
    }

    private fun animateUp(v : Double, col: Int, bounce : Int) {
        var velocity = v
        val animation = object : AnimationTimer() {
            override fun handle(now: Long) {
                translateY -= velocity
                velocity -= 1
                if (velocity <= 0) {
                    stop()
                    animateDown(col, bounce - 1)
                }
            }
        }
        animation.start()
    }
    private fun animateBack() {
        val temp = if (player == Player.ONE) {
            0.0
        } else {
            player2X
        }
        var velocityX = (translateX - temp) / 50
        var velocityY = translateY / 50
        val animation = object : AnimationTimer() {
            override fun handle(now: Long) {
                translateX -= velocityX
                translateY -= velocityY
                velocityX *= 0.99
                velocityY *= 0.99
                if (abs(translateX - temp) < 5) {
                    stop()
                }
            }
        }
        animation.start()
    }
    private fun isDroppable(col: Int) : Boolean {
        return if (col < 0 || col > Model.width - 1) {
            false
        } else {
            Model.getGround(col) > 0
        }
    }

    override fun changed(
        observable: ObservableValue<out Player>?,
        oldValue: Player?,
        newValue: Player?
    ) {
        if (newValue != null) {
            isVisible = (newValue == player)
            translateX = if(player == Player.ONE){
                0.0
            } else {
                player2X
            }
            translateY = 0.0
        }
    }
}