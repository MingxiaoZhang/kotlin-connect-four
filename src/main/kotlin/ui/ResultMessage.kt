package ui

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.control.Label
import javafx.scene.shape.Rectangle
import model.Model
import model.Player

class WinMessage : Label(""), ChangeListener<Player> {
    init {
        isVisible = false
        Model.onGameWin.addListener(this)
    }

    override fun changed(observable: ObservableValue<out Player>?, oldValue: Player?, newValue: Player?) {
        isVisible = true
        text = "Player $newValue won!"
    }
}