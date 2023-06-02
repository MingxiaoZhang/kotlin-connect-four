import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import model.Model
import model.Player
import scenes.Game
import scenes.Menu
import ui.*

class Main : Application() {
    override fun start(stage: Stage) {
        stage.title = "Kotlin Connect-4"
        stage.scene = Scene(Menu(true), 600.0, 400.0)
        stage.isResizable = false
        stage.show()

        val sceneListener =
            ChangeListener {
                    _: ObservableValue<out Boolean>?,
                    _: Boolean?,
                    newValue: Boolean ->
                if (!newValue) {
                    stage.scene = Scene(Menu(false), 600.0, 400.0)
                } else {
                    stage.scene = Scene(Game.getGame(),
                        240.0 + gridSize * Model.width,
                        gridSize * Model.height + 120.0)
                }
            }
        Model.onGameStart.addListener(sceneListener)
    }
}