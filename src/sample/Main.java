package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.TabableView;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{


       Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Warehouse in JavaFX");
        primaryStage.setScene(new Scene(root, 520, 340));
        primaryStage.setResizable(false);




//        window = primaryStage;
//        window.setTitle("New Frame");
//
//        TableColumn<Equipment, Integer> idColumn = new TableColumn<>("ID");
//        idColumn.setMinWidth(200);
//        idColumn.setCellValueFactory(new PropertyValueFactory<>(("id")));
//
//        TableColumn<Equipment, Integer> snColumn = new TableColumn<>("Serial Number");
//        snColumn.setMinWidth(200);
//        snColumn.setCellValueFactory(new PropertyValueFactory<>(("serialNumber")));
//
//        TableColumn<Equipment, Integer> typeColumn = new TableColumn<>("Type");
//        typeColumn.setMinWidth(200);
//        typeColumn.setCellValueFactory(new PropertyValueFactory<>(("type")));
//
//        table = new TableView<>();
//        table.setItems(EquipmentList.getEquipment());
//        table.setItems(EquipmentList.addEquipment(4,"<","new"));
//        table.getColumns().addAll(idColumn, snColumn, typeColumn);
//        button = new Button("ok");
//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(table, button);
//        Scene scene = new Scene(vBox);
//        primaryStage.setScene();


        primaryStage.show();


    }


    public static void main(String[] args) {

        launch(args);
    }
}

