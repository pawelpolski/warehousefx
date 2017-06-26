package polskipawel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import polskipawel.controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main extends Application {


    /**
     * view javafx initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/polskipawel/view/view.fxml"));
        primaryStage.setTitle("Warehouse in JavaFX");
        primaryStage.setScene(new Scene(root, 710, 640));
        primaryStage.setResizable(false);
        primaryStage.show();
        Controller controller = new Controller();
       //controller.startView(primaryStage).show();


        controller.justConnect();
    }
    /**
     * Start application
     */
    public static void main(String[] args) throws IOException {
        launch(args);

    }
}

