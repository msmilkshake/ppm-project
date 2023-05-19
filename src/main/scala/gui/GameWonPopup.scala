package gui

import core.Cells.{Blue, Red}
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}
import javafx.stage.Stage

class GameWonPopup {

  @FXML
  var lblMessage: Label = _
  @FXML
  var btnMenu: Button = _
  
  var stage: Stage =_

  GameWonPopup.instance = this

  def update(): Unit = {
    val (playerName, color) = MainWindow.c.gameState.winner match {
      case Some(Red) => ("Player", "red")
      case Some(Blue) => ("Computer", "dodgerblue")
    }
    stage.setTitle(f"$playerName WINS!")
    lblMessage.setText(f"$playerName won the game!")
    lblMessage.setStyle(f"-fx-text-fill: $color;")
  }
  
  def setStage(stage: Stage): Unit = {
    this.stage = stage
    update()
  }
  
  def btnMenuOnClicked(): Unit = {
    btnMenu.getScene.getWindow.hide()
  }

}

object GameWonPopup {
  var instance: GameWonPopup = _
}
