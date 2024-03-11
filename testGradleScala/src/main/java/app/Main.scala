package app;

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.scene.control.Label
import javafx.stage.Stage
import pureconfig.{ConfigReader, ConfigSource}
import pureconfig.generic.auto._

import java.net.URL



case class ChatConfig(val host:String, val port:Integer);

class Main extends Application {

    override def start(primaryStage: Stage) :Unit = {
        val loader:FXMLLoader = new FXMLLoader();
        val xmlUrl:URL = getClass().getResource("/chatStage.fxml");
        loader.setLocation(xmlUrl);
        val root:Parent = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        val config = ConfigSource.default.at("chat-config").load[ChatConfig]


    }
}

/*
object Main{
    def main(args: Array[String]):Unit = {

        System.out.printf("Hello and welcome!");
        var arr:List[Integer] = List(1,2,3);

        for (num <- arr) {

            System.out.println("i = " + num);
        }
    }
}

 */