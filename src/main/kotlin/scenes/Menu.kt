package scenes

import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import model.Model
import ui.StartControl

class Menu(initial: Boolean) : VBox(){
    private val size = ChoiceBox(FXCollections.observableArrayList(4, 5, 6, 7, 8))
    init {
        if (initial) {
            children.add(Label("Connect 4"))
        } else {
            if (Model.onGameDraw.value) {
                children.add(Label("Draw"))
            } else {
                children.add(Label("Player #${Model.onGameWin.value} won!"))
            }
            children.add(Label("New Game"))
        }
        children.add(Label("Select size: "))
        children.add(size)
        children.add(StartControl(size))
        alignment = Pos.CENTER
        spacing = 10.0
    }
}