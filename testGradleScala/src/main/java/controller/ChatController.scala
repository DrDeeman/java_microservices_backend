package controller

import javafx.fxml.FXML
import javafx.scene.control.TextField
import scalafxml.core.macros.sfxml

import scala.beans.BeanProperty


class ChatController {

  @FXML
   private var chatField: TextField = _

  @FXML
   private def handlerClick(): Unit = {
    System.out.println(chatField.getText());
  }
}
