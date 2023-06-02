package ui

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.control.Label
import model.Model
import model.Player

class DrawMessage : Label(""), ChangeListener<Boolean> {
    init {
        isVisible = false
        Model.onGameDraw.addListener(this)
    }

    override fun changed(observable: ObservableValue<out Boolean>?, oldValue: Boolean?, newValue: Boolean?) {
        isVisible = true
        text = "Draw"
    }
}