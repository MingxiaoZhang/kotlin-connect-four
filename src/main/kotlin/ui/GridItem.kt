package ui

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape

class GridItem(posX: Double, posY: Double,
               size: Double
) {
    private val rect = Rectangle(posX, posY, size, size)
    private val circle = Circle(posX + size / 2, posY + size / 2, size / 2 - 7)
    val item: Shape = Shape.subtract(rect, circle).apply{
        fill = Color.BLUE
        stroke = Color.BLACK
    }
}