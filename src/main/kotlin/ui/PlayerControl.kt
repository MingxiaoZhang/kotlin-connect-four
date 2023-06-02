package ui

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import model.Model
import model.Player

class StartControl(size : ChoiceBox<Int>) : Button("Click here to start game!") {
    init {
        prefWidth = 200.0
        prefHeight = 50.0
        onAction = EventHandler {
            if (size.value != null) {
                Model.width = size.value
                Model.height = size.value - 1
                Model.startGame()
            }
        }
    }
}