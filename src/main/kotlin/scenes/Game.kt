package scenes

import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import model.Player
import ui.ConnectFourGrid
import ui.DragControl
import ui.DrawMessage
import ui.WinMessage

object Game {
    fun getGame() : AnchorPane{
        return AnchorPane(
            DragControl(55.0, 65.0, Color.RED, Player.ONE),
            DragControl(55.0, 65.0, Color.YELLOW, Player.TWO),
            Label("Player #1").apply{
                AnchorPane.setTopAnchor(this, 10.0)
                AnchorPane.setLeftAnchor(this, 30.0)
            }, Label("Player #2").apply{
                AnchorPane.setTopAnchor(this, 10.0)
                AnchorPane.setRightAnchor(this, 30.0)
            }, ConnectFourGrid().apply{
                AnchorPane.setTopAnchor(this, 100.0)
                AnchorPane.setLeftAnchor(this, 120.0)
            }
        )
    }
}